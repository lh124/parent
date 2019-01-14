package io.yfjz.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.service.backup.FileService;
import io.yfjz.service.backup.impl.FileServiceImpl;
import io.yfjz.utils.Constant;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** 
* @Description: 田金海 
* @Param: 备份数据库
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/30 11:49
* @Tel  17328595627
*/ 
public class AnnotationWebSocketHandler extends TextWebSocketHandler {

    private Logger logger= Logger.getLogger(AnnotationWebSocketHandler.class);

    private FileService fileService=new FileServiceImpl();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String path=this.getDir();
        Constant.SHELLPATH=path+"shell/";
        if(session!=null){
            TextMessage msg = new TextMessage("链接成功了！");
            session.sendMessage(msg);
        }
        //解决脚本没有执行权限问题
        String chmodPath = Constant.SHELLPATH + "*.sh"; // zlib_decompress 路径
        String template = "chmod 777 %s";
        String command = String.format(template, chmodPath);
        String[] cmd = new String[] { "/bin/sh", "-c", command };
        try {
            final Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            System.out.println("执行完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String path=getDir();
        final JSONObject json = (JSONObject) JSON.parse(message.getPayload());
        Constant.SHELLPATH=path+"shell";
        if("restore".equalsIgnoreCase(json.get("opt").toString().trim())){
            try {
                //解决脚本没有执行权限

                // exec(session,Constant.SHELLPATH,"/unencrypt.sh");
                exec(session, Constant.SHELLPATH,Constant.RESTORESHELL);
                // execRestore(session, Constant.SHELLPATH,Constant.RESTORESHELL);
            } catch (Exception e) {
                e.printStackTrace();
                session.sendMessage(new TextMessage(e.getMessage()));
            }
        }else if("backup".equalsIgnoreCase(json.get("opt").toString().trim())){
            try {
                //解决脚本没有执行权限
                //exec(session,Constant.SHELLPATH,"chmod 775 "+Constant.SHELLPATH+"*.sh");
                exec(session, Constant.SHELLPATH,Constant.BACKUPSHELL);
            } catch (Exception e) {
                e.printStackTrace();
                session.sendMessage(new TextMessage(e.getMessage()));
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

    }


    private void exec(WebSocketSession session,String path,String shell) throws Exception{
        final Process process = fileService.execShell(path, shell);
        BufferedReader br = null;
         try{
             br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = null;
            while (null != (output = br.readLine()))
            {
                session.sendMessage(new TextMessage(output));
                logger.debug(output);
            }
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getDir(){
        Thread thread = Thread.currentThread();
        return thread.getContextClassLoader().getResource("/").getPath();
    }


}
