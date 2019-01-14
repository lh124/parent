package io.yfjz.controller.backup;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import io.yfjz.service.backup.FileService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @Description: 数据库备份
* @Param:
* @return:
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/30 10:14
* @Tel  17328595627
*/ 
@Controller
@RequestMapping(value = "/file")
public class BackupController {


    private Logger logger= Logger.getLogger(BackupController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload")
    public String toFileUpload(){
        return "fileupload";
    }

    @RequestMapping(value = "/download")
    public String toFiledownload(){
        return "filedownload";
    }

    /**
     * 上传备份文件
     * @param request
     * @param response
     * @param file
     */
    @RequestMapping(value="/uploadfile",method= RequestMethod.POST)
    @ResponseBody
    public Map uploadfile(HttpServletRequest request, HttpServletResponse response, @RequestParam("RestoreFile") MultipartFile file){
        final Map<String, Object> map = fileService.uploadFile(file, Constant.UPLOADFILEPATH);
        return map;
    }

    /**
     * 获取备份文件或者恢复文件的的所有的集合 type的值即为条件
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getdownloadfiles")
    @ResponseBody
    public R getAllFileWithPage(HttpServletRequest request, HttpServletResponse response){
        final Integer page=request.getParameter("page")!=null?Integer.valueOf( request.getParameter("page")):0;
        final Integer limit=request.getParameter("limit")!=null?Integer.valueOf( request.getParameter("limit")):0;
        final String  type=request.getParameter("type");
        String destination=null;
        if(type!=null&&"upload".equalsIgnoreCase(type)){
            destination=Constant.UPLOADFILEPATH;
        }else if(type!=null&&"download".equalsIgnoreCase(type)){
            destination=Constant.DOWNLOADFILEPATH;
        }else{
            destination=Constant.DOWNLOADFILEPATH;
        }
        final Map<String, Object> allFilesWithPage = fileService.getAllFilesWithPage(destination, page, limit);
        PageUtils pageUtil = new PageUtils((List)allFilesWithPage.get("rows"),Integer.parseInt(allFilesWithPage.get("total").toString()), limit, page);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 下载文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/downloadfile")
    public void download(HttpServletRequest request, HttpServletResponse response)throws Exception {
        String trans_filename =URLDecoder.decode(URLDecoder.decode(request.getParameter("filename"),"UTF-8"),"UTF-8");
        File file = new File(Constant.DOWNLOADFILEPATH+File.separator+trans_filename);
        //设置响应报头
        response.setContentType("application/x-msdownload");
        //获取请求头
		String header = request.getHeader("User-Agent");
		String encode = URLEncoder.encode(trans_filename, "utf-8");
		System.out.println(encode);
		if (header!=null&& header.contains("LCTE")) {
            response.setHeader("Content-Disposition", "attachment; filename="+encode);
		}else{
			String name1=new String(trans_filename.getBytes("utf-8"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename="+name1);

		}
		Files.copy(Paths.get(Constant.DOWNLOADFILEPATH+File.separator+trans_filename), response.getOutputStream());
    }

    /**
     * 备份文件
     * @param response
     * @param request
     */
    @RequestMapping(value="/backup")
    public String backup(HttpServletResponse response, HttpServletRequest request){
        final String opt = request.getParameter("opt");
        Object json=null;
        final String dir = this.getDir();
        Constant.SHELLPATH=dir+"/shell";
        if(StringUtil.isEmpty(opt)&&"backup".equalsIgnoreCase(opt)){
            fileService.execShell(Constant.SHELLPATH,"chmod 775 *.sh");
            final Process process = fileService.execShell(Constant.SHELLPATH, Constant.BACKUPSHELL);
            try {
                InputStreamReader ir = new InputStreamReader(process.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String line=null;
                process.waitFor();
                while ((line = input.readLine()) != null) {
                    logger.debug(line);
                }
                final Map<String, Object> map = fileService.backupJadge(process);
                json= JSON.toJSON(map);
            } catch (InterruptedException e) {
                e.printStackTrace();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message","failure");
                json=jsonObject;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","failure");
            json=jsonObject;
        }
        return JSON.toJSONString(json);
    }


    public String getDir(){
        Thread thread = Thread.currentThread();
        return thread.getContextClassLoader().getResource("/").getPath();
    }
    /**
     * 下载最新文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/downloadNew")
    public void downloadNew(HttpServletRequest request, HttpServletResponse response,String type)throws Exception {
        String destination=Constant.DOWNLOADFILEPATH;
        Map<String, Object> allFilesWithPage = fileService.getAllFilesWithPage(destination, 1, 1000);
        List<Map<String,Object>> list=null;
        if (allFilesWithPage.get("rows")!=null) {
            list=(List<Map<String,Object>>)allFilesWithPage.get("rows");
        }
        String fileName="";
        if(list!=null&&list.size()>0){
            fileName=list.get(0).get("fileName").toString();
        }else{
            throw  new RRException("没有备份文件可以下载,请及时备份数据！");
        }
        String trans_filename =URLDecoder.decode(URLDecoder.decode(fileName,"UTF-8"),"UTF-8");
        File file = new File(Constant.DOWNLOADFILEPATH+File.separator+trans_filename);
        //设置响应报头
        response.setContentType("application/x-msdownload");
        //获取请求头
        String header = request.getHeader("User-Agent");
        String encode = URLEncoder.encode(trans_filename, "utf-8");
        System.out.println(encode);
        if (header!=null&& header.contains("LCTE")) {
            response.setHeader("Content-Disposition", "attachment; filename="+encode);
        }else{
            String name1=new String(trans_filename.getBytes("utf-8"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename="+name1);

        }
        if("download".equals(type)){
          Files.copy(Paths.get(Constant.DOWNLOADFILEPATH+File.separator+trans_filename), response.getOutputStream());
        }
    }
    /**
     * 下载最新文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/judgeFile")
    @ResponseBody
    public Map judgeFile(HttpServletRequest request, HttpServletResponse response)throws Exception {
        String destination=Constant.DOWNLOADFILEPATH;
        Map<String, Object> allFilesWithPage = fileService.getAllFilesWithPage(destination, 1, 1000);
        List<Map<String,Object>> list=null;
        if (allFilesWithPage.get("rows")!=null) {
            list=(List<Map<String,Object>>)allFilesWithPage.get("rows");
        }
        HashMap<Object, Object> temp = new HashMap<>();
        String fileName="";
        if(list!=null&&list.size()>0){
            temp.put("code","1");
        }else{
            temp.put("code","0");
           temp.put("msg","没有备份文件可以下载,请及时备份数据！");
        }
        return temp;

    }
}
