package io.yfjz.utils;

import io.yfjz.utils.encrypt.DESUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * file文件操作工具
 * Created by lq on 2017/5/23.
 */
public class FileUtils {

    private static final int BUFFERSIZE = 1024 * 1024 * 5;// 文件缓冲区大小5M设置

    /**
     * 将org.springframework.web.multipart.MultipartFile 转为 java.io.File
     *
     * @param mfile
     * @return
     * @throws IOException
     */
    public static File multipartToFile(MultipartFile mfile) throws IllegalStateException, IOException {
        File tmpF = new File(mfile.getOriginalFilename());
        mfile.transferTo(tmpF);
        return tmpF;
    }

    /**
     * 文件下载
     *
     * @param request
     * @param filePath 文件路径
     * @param fileName
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
        System.out.println("***********************进入文件下载*****************" + fileName + "      filePath " + filePath);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream is = null;
        try {
            URL url = new URL(filePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            bis = new BufferedInputStream(is);
            response.addHeader("Connection", "Keep-Alive");
            response.setContentType("application/octet-stream");
            if (StringUtils.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.replaceAll(" ", "").getBytes("GB2312"), "ISO_8859_1"));
            } else {
                response.addHeader("Content-Disposition", "attachment;filename=" + filePath.substring(filePath.lastIndexOf("/")));
            }
            OutputStream out = response.getOutputStream();//获取输出流
            byte[] buffer = new byte[BUFFERSIZE];
            int byteRead;
            while ((byteRead = bis.read(buffer)) != -1) {
                out.write(buffer, 0, byteRead);//向输出流中输出数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
                if (null != bos) {
                    bis.close();
                }
                if (null != bos) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("**********************文件下载失败****************************");
            }
        }
    }


    /**
     * 执行远程服务器文件删除
     *
     * @param url
     * @param filePath 文件远程服务器地址
     * @return
     */
    public static String deleteFromServer(String url, String filePath) {
        //org.apache.http.NameValuePair 4.4.6 版本
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("fUrl", filePath));//防止恶意猜猜参数名
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String message = null;
        UrlEncodedFormEntity uefEntity;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            uefEntity = new UrlEncodedFormEntity(param, "UTF-8");
            httppost.setEntity(uefEntity);
            response = httpClient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    message = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));// 打印响应内容
                }
                EntityUtils.consume(resEntity);// 销毁
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            message = "删除文件异常";
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }


    /**
     * 工具类
     * 将org.springframework.web.multipart.MultipartFile 转为 java.io.File
     *
     * @param request
     * @param mfile
     * @return
     * @throws IOException
     * @author 刘琪
     * @date 2017-11-23 pm 16:41:33
     */
    public static File multipartToFile(HttpServletRequest request, MultipartFile mfile) throws IllegalStateException, IOException {
        File tmpF = new File(mfile.getOriginalFilename());
        mfile.transferTo(tmpF);
        return tmpF;
    }

    /**
     * @method_name: getFileSize
     * @describe: 获取文件的大小转换方法
     * @param: [file]
     * @return: java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/11  9:27
     **/
    public static String getFileSize(File file) {
        long size = file.length();
        long result = size / 1024 / 1024;
        if (result < 1) {
            result = 1;
        }
        return result + "MB";
    }


    public String getAllSQLToExcute() {
        try {
            byte[] buffer = null;
            File file = new File("C:\\Users\\jytc\\Downloads\\update5.sql");
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            String str = new String(buffer, "UTF-8");
            byte[] key = new BASE64Decoder().decodeBuffer(PropertiesUtils.getMapValue("key"));
            byte[] bytes1 = new BASE64Decoder().decodeBuffer(str);
            byte[] bytes = DESUtils.des3DecodeECB(key, bytes1);
            String s = new String(bytes, "UTF-8");
            System.out.println(s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
