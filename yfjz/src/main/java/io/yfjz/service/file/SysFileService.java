package io.yfjz.service.file;

import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘琪
 * @class_name: SysFileService
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-07-23 17:09
 */
public interface SysFileService {
    
    /**
     * @method_name: uploadFile
     * @describe: 文件上传实现
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/23  17:32
     **/
    R uploadFile(HttpServletRequest request, HttpServletResponse response);

    R deleteFileFromServer(String[] ids);
}
