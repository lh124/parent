package io.yfjz.controller.statistics;

import io.yfjz.service.statics.InoculateService;
import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 刘琪
 * @class_name: InoculateLogsController
 * @Description: 接种日志统计报表
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-09-05 10:05
 */
@Controller
@RequestMapping("inoculatelogs")
public class InoculateLogsController {

    @Autowired
    InoculateService inoculateService;

    /**
     * @method_name: inoculateLogs
     * @describe: 查询接种日期统计报表
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/5  11:18
     **/
    @RequestMapping("queryInoculateLogs")
    @ResponseBody
    public R inoculateLogs(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "chilCommittees[]", required = false) String[] chilCommittees,
                           @RequestParam(value = "chilResidences[]", required = false) String[] chilResidences,
                           @RequestParam(value = "infostatus[]", required = false) String[] infostatus,
                           @RequestParam(value = "biotypes[]", required = false) String[] biotypes,
                           @RequestParam(value = "bioNos[]", required = false) String[] bioNos,
                           @RequestParam(value = "inocDoctors[]", required = false) String[] inocDoctors,
                           @RequestParam(value = "schools[]", required = false) String[] schools,
                           @RequestParam(value = "inocbatchno[]", required = false) String[] inocbatchno) {
        Map paramMap = HttpServletRequestToMapUtils.converToMap(request);
        return inoculateService.queryInoculateLogs(paramMap,chilCommittees,chilResidences,infostatus,biotypes,bioNos,schools,inocDoctors,inocbatchno);
    }
}
