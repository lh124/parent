//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.task;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("testTask")
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysUserService sysUserService;

    public TestTask() {
    }
    @Test
    public void test(String params) {
        this.logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        SysUserEntity user = this.sysUserService.queryObject("1");
        System.out.println(ToStringBuilder.reflectionToString(user));
    }

    public void test2() {
        this.logger.info("我是不带参数的test2方法，正在被执行");
    }
}
