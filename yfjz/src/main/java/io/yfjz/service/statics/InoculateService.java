package io.yfjz.service.statics;

import io.yfjz.utils.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 刘琪
 * @class_name: InoculateService
 * @Description: 接种日志统计报表
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-09-05 11:11
 */
public interface InoculateService {
    
    /**
     * @method_name: queryInoculateLogs
     * @describe: 查询接种日期统计报表
     * @param: [paramMap, chilCommittees, chilResidence, infostatus, biotypes, bioNos, school]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/5  19:39
     **/
    R queryInoculateLogs(Map paramMap, String[] chilCommittees, String[] chilResidences, String[] infostatus, String[] biotypes, String[] bioNos, String[] schools,String[] inocDoctors,String[] inocbatchno);


}
