//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysPageController {

    private static Logger logger=Logger.getLogger(SysPageController.class);
    public SysPageController() {
    }

/**
 * @method_name: page2
 * @describe:
 * @param: [pre:前缀路径, url:后缀路径]
 * @return: java.lang.String
 * @author: 刘琪
 * @QQ: 1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/7/30  23:36
 **/
   @RequestMapping({"{pre}/{url}.html"})
    public String page(@PathVariable("pre") String pre,@PathVariable("url") String url) {
       logger.info(">>>>>>>>>>>跳转路径>>>>>>>>>>"+pre+"/" + url + ".html");
       return pre+"/" + url + ".html";
   }
}
