package io.yfjz.service.backup.impl;


import io.yfjz.service.backup.FileService;
import io.yfjz.utils.Constant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    private org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(FileServiceImpl.class);


    @Override
    public Map<String, Object> getAllFilesWithPage(String dirPath, Integer startPage, Integer pageCount) {
        File dirPathFile=new File(dirPath);
        List<Map<String, Object>> files=new ArrayList<Map<String, Object>>();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> result=new HashMap<String, Object>();
        int count=0;
        if (dirPathFile.exists()) {
            File[] allfiles = dirPathFile.listFiles();
            for (File file:allfiles) {
                if(file.isFile()){
                   Map<String,Object> map=new HashMap<String,Object>(){{
                       put("fileName",file.getName());
                       put("fileSize",file.length());
                       long time = file.lastModified();
                       put("lastModifyTime",df.format(new Date(time)));
                   }};
                   files.add(map);
                   count++;
                }
            }
        }
        files.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date o1Date = sdf.parse(o1.get("lastModifyTime").toString());
                    Date o2Date = sdf.parse(o2.get("lastModifyTime").toString());
                    if(o1Date.getTime()>o2Date.getTime()){
                        return -1;
                    }else if(o1Date.getTime()<o2Date.getTime()){
                        return 1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        result.put("total",count);
        result.put("rows",files);
        return result;
    }

    @Override
    public Map<String,Object> uploadFile(MultipartFile file, String destination) {
        Map<String,Object> result=new HashMap<>();
        if(!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            File newfile = new File(destination,filename);
            File destinationFile = new File(Constant.UPLOADFILEPATH + File.separator + Constant.FILENAME);
            //判断路径是否存在，如果不存在就创建一个
            if (!newfile.getParentFile().exists()) {
                newfile.getParentFile().mkdirs();
            }
            if(destinationFile.exists()){
                destinationFile.delete();
            }
            try {
                file.transferTo(newfile);
                Files.copy(newfile.toPath(),destinationFile.toPath());
                newfile.delete();
                result.put("message","上传成功");
               // execShell(Constant.UPLOADFILEPATH + File.separator + Constant.FILENAME," dd if='/home/restore/restore_file.des3' |openssl des3 -d -k 123456|tar -zvxf - -C /home/restore");
                // unpack(Constant.UPLOADFILEPATH + File.separator + Constant.FILENAME);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                result.put("message","上传失败");
                return result;
            }
        } else {
            result.put("message","文件是空的");
            return result;
        }
    }

    @Override
    public Process execShell(String shellPath,String shell) {
        String cmd=shellPath+shell;
        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.redirectErrorStream(true);
        Process process=null;
        try {
            process = builder.start();
            //process = Runtime.getRuntime().exec(cmd);
            return process;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> backupJadge(Process process) {
        Map<String,Object> result=new HashMap<>();
       try{
        InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String line=null;
        process.waitFor();
        while ((line = input.readLine()) != null) {
            if("mysql backup end!".equalsIgnoreCase(line.trim())){
                result.put("message","success");
            }else{
                result.put("message","failure");
            }
        }
       }catch (Exception e){
           result.put("message","failure");
           return result;
       }
       return result;
    }
    private void unpack(String file){
        String template = "dd if='restore_file.des3' |openssl des3 -d -k 123456|tar zvxf -";
        String command = String.format(template, file);
        String[] cmd = new String[] { "/bin/sh", "-c", command };
        try {
            final Process process = Runtime.getRuntime().exec(cmd);
            InputStream inputStream = process.getInputStream();
            InputStreamReader ir = new InputStreamReader(inputStream);
            LineNumberReader input = new LineNumberReader(ir);
            String line=null;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                logger.debug("====>>>>"+line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
