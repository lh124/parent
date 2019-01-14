package io.yfjz.controller.file;

import io.yfjz.service.file.SysFileService;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 远程文件操作控制器
 * Created by lq on 2017/6/10.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    SysFileService sysFileService;

    /**
     * 删除远程服务器文件
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletefile")
    public R deleteFileFromServer(HttpServletRequest request,HttpServletResponse response,@RequestBody String[] ids) {
        return sysFileService.deleteFileFromServer(ids);
    }



    @ResponseBody
    @RequestMapping(value = "/commonuploadfile")
    public R uploadFile2Server(HttpServletRequest request,HttpServletResponse response){
        return sysFileService.uploadFile(request,response);
    }

}
