//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;


import com.alibaba.fastjson.JSON;
import io.yfjz.service.sys.SysGeneratorService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping({"/sys/generator"})
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    public SysGeneratorController() {
    }

    @ResponseBody
    @RequestMapping({"/list"})
    @RequiresPermissions({"sys:generator:list"})
    public R list(String tableName, Integer page, Integer limit) {
        HashMap map = new HashMap();
        map.put("tableName", tableName);
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        List list = this.sysGeneratorService.queryList(map);
        int total = this.sysGeneratorService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(list, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping({"/code"})
    @RequiresPermissions({"sys:generator:code"})
    public void code(String tables, HttpServletResponse response) throws IOException {
        String[] tableNames = new String[0];
        tableNames = (String[])JSON.parseArray(tables).toArray(tableNames);
        byte[] data = this.sysGeneratorService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"yfjz.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
