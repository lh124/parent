package io.yfjz.webservice;


import javax.jws.WebService;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 使用spring配置cxf客户端 上传数据到平台
 * @Date 12:20 2018/11/15
 */
//注意，该出的targetNamespace的值必须和webService服务项目中定义的必须一致，否则调用不成功
@WebService(targetNamespace = "http://webservice.yfjz.io/", name = "UploadPlatformWebservice")
public interface UploadPlatformWebservice {
    /**
     * 上传方法
     * @param orgid 加密过的机构编码
     * @param password 验证密码
     * @param tablesNames 经过压缩和加密参数
     * @return
     */

   public String uploadingPlatformData(String orgid, String password, String tablesNames);
}
