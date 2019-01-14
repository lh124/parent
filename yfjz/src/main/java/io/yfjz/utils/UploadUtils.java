package io.yfjz.utils;

import java.io.File;

/**
 * @class_name: UploadUtils
 * @describe: 文件上传工具类
 * @author: 刘琪
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date:  2017/12/27 17:36
 **/
public class UploadUtils {
    private static UploadUtils uploadUtils;//单利模式
    private UploadUtils() {
        //私有构造函数，不让外部实例化
    }

    /**
     * @describe: 单例模式获取上传工具类实例
     * @method_name: getInstance
     * @param []
     * @return io.yfjz.utils.UploadUtils
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/25  21:07
     **/
    public static UploadUtils getInstance() {
        if (null == uploadUtils) {
            uploadUtils = new UploadUtils();
        }
        return uploadUtils;
    }

    /**
     * 上传服务器地址
     */
    private static  String YFJZ_UPLOAD_URL;//上传文件路径
    private static  String YFJZ_DELETE_URL;//删除文件路径
    static{
        YFJZ_UPLOAD_URL= PropertiesUtils.getMapValue("YFJZ_UPLOAD_URL");//文件的跟路径
        YFJZ_DELETE_URL= PropertiesUtils.getMapValue("YFJZ_DELETE_URL");//文件的跟路径
    }

 
    /**
     * @describe: 删除远程服务器文件
     * @method_name: deletefileUploadFile2Server
     * @param [filepath]
     * @return java.lang.String
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017/12/25  21:08
     **/
    public String deletefileFromServer(String filepath) {
       return HttpClientUtils.deleteFromServer(YFJZ_DELETE_URL, filepath);
    }

 
    /**
     * @method_name: fileUploadFileToServer
     * @describe: 文件上传
     * @param: [file]
     * @return: java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/23  17:04
     **/
    public String fileUploadFileToServer(File file) {
        return HttpClientUtils.upload(YFJZ_UPLOAD_URL,file);
    }
}