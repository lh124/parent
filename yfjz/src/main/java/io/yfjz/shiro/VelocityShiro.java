package io.yfjz.shiro;

import io.yfjz.utils.Constant;
import io.yfjz.utils.PropertiesUtils;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @class_name: VelocityShiro
 * @describe: shiro自定义标签使用
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date:  2017/12/20 9:47
 **/
public class VelocityShiro {
    public VelocityShiro() {

    }

    /**
     * @method_name: hasPermission
     * @describe: 是否有权限  html页面的每个$shiro.hasPermission（“”）都会触发这个方法，然后判断是否有权限
     * @param [permission:权限标识符]
     * @return boolean
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/20  9:50
     **/
    public boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }


    /**
     * @method_name: getBaseInfo
     * @describe: 根据参数名获取session中的缓存值
     * @param: [name]
     * @return: java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/8  14:11
     **/
    public String getBaseInfo(String name) {
        if (StringUtils.isEmpty(name)){
            return "请传入参数";
        }else if (name.equalsIgnoreCase("orgId")){//当前登录用户所属机构编码
            return Constant.GLOBAL_ORG_ID==null?"":Constant.GLOBAL_ORG_ID;
        }else if (name.equalsIgnoreCase("orgName")){//当前登录用户所属机构名称
            return Constant.GLOBAL_ORG_NAME==null?"":Constant.GLOBAL_ORG_NAME;
        }else if (name.equalsIgnoreCase("userId")){//当前登录账户编码
            return ShiroUtils.getUserEntity().getUserId();
        }else if (name.equalsIgnoreCase("userName")){//当前登录用户名称
            return ShiroUtils.getUserEntity().getUsername();
        }else if (name.equalsIgnoreCase("realName")){//当前登录用户真实名称
            return ShiroUtils.getUserEntity().getRealName()==null?"":ShiroUtils.getUserEntity().getRealName();
        }else if (name.equalsIgnoreCase("regTowerId")){//登记台  工作台编码
            return ShiroUtils.getUserEntity().getRegisterTowerId();
        }else if (name.equalsIgnoreCase("inocTowerId")){//接种台  工作台编码
            return ShiroUtils.getUserEntity().getInoculateTowerId();
        }else if (name.equalsIgnoreCase("chiProId")){//儿保台  工作台编码
            return ShiroUtils.getUserEntity().getChildProtectionTowerId();
        }else if (name.equalsIgnoreCase("preId")){//预检台  工作台编码
            return ShiroUtils.getUserEntity().getPreCheckId();
        }else if (name.equalsIgnoreCase("regTowerName")){//登记台名称
            return ShiroUtils.getUserEntity().getRegisterTowerName();
        }else if (name.equalsIgnoreCase("inocTowerName")){//接种台名称
            return ShiroUtils.getUserEntity().getInoculateTowerName()==null?"":ShiroUtils.getUserEntity().getInoculateTowerName();
        }else if (name.equalsIgnoreCase("chiProName")){//儿保台名称
            return ShiroUtils.getUserEntity().getChildProtectionTowerName();
        }else if (name.equalsIgnoreCase("preName")){//预检台名称
            return ShiroUtils.getUserEntity().getPreCheckName();
        }else if (name.equalsIgnoreCase("queuer.host")){//当前服务器ip地址
            return PropertiesUtils.getMapValue(name);
        }else if (name.equalsIgnoreCase("queuer.regType")){//当前所属登记队列类型
            return PropertiesUtils.getMapValue(name);
        }else if (name.equalsIgnoreCase("queuer.inocType")){//当前所属接种队列类型
            return PropertiesUtils.getMapValue(name);
        }else if (name.equalsIgnoreCase("queuer.preType")){//当前所属预检队列类型
            return PropertiesUtils.getMapValue(name);
        }else if (name.equalsIgnoreCase("queuer.healthType")){//当前所属儿保队列类型
            return PropertiesUtils.getMapValue(name);
        }else if (name.equalsIgnoreCase("towers")){//获取所有选择的工作台
            return PropertiesUtils.getMapValue(name);
        }else{
            return "您输入的参数名没有匹配的值，请检查参数名";
        }
    }
}
