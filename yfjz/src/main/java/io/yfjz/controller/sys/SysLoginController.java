//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.basesetting.TBaseTowerService;
import io.yfjz.service.sys.SysDepartUserService;
import io.yfjz.service.sys.SysUserService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.PropertiesUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.UrlUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class SysLoginController {
    @Autowired
    private Producer producer;

    @Autowired
    private SysDepartUserService sysDepartUserService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    TBaseTowerService tBaseTowerService;

    public SysLoginController() {
    }

    @RequestMapping({"captcha.jpg"})
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        String text = this.producer.createText();
        BufferedImage image = this.producer.createImage(text);
        ShiroUtils.setSessionAttribute("KAPTCHA_SESSION_KEY", text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @ResponseBody
    @RequestMapping(
            value = {"/sys/login"},
            method = {RequestMethod.POST}
    )
    public R login(HttpServletRequest request, String username, String password, String captcha, String remember) throws IOException {
        try {
            Subject e = ShiroUtils.getSubject();
            password = (new Sha256Hash(password)).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            e.login(token);
            //将用户的一些基本使用的信息存放session中
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            HttpSession session = httpServletRequest.getSession();
            //把用户信息保存到session
            SysUserEntity sysUserEntity = (SysUserEntity) e.getPrincipal();
            List<String> list = sysUserService.getLoginUserRoleName(sysUserEntity.getUserId());
            sysUserEntity.setRoleNames(list.toString());
            SysUserEntity dbEntity = sysDepartUserService.queryOrgInfoByUserId(sysUserEntity.getUserId());

            if (dbEntity == null || org.apache.commons.lang3.StringUtils.isEmpty(dbEntity.getOrgId())){
                return R.error(-1, "账户尚未绑定到机构，请先登录平台绑定到机构后再登录!");
            }

            sysUserEntity.setOrgId(dbEntity.getOrgId());
            sysUserEntity.setOrgName(dbEntity.getOrgName());
            session.setAttribute("SYS_USER_INFO", sysUserEntity);
            session.setAttribute("GLOBAL_ORG_ID", dbEntity.getOrgId());//机构编码
            session.setAttribute("GLOBAL_ORG_NAME", dbEntity.getOrgName());//机构名称
            session.setAttribute("GLOBAL_USERID", sysUserEntity.getUserId());//用户编码
            session.setAttribute("GLOBAL_USERNAME", sysUserEntity.getUsername());//用户名称

            //缓存到全局变量
            Constant.GLOBAL_ORG_ID = dbEntity.getOrgId();
            Constant.GLOBAL_ORG_NAME = dbEntity.getOrgName();
        } catch (Exception e1) {
            e1.printStackTrace();
            return R.error(-1, "账户验证失败");
        }
        return R.ok();
    }

    /**
     * @method_name: getTowerList
     * @describe: 获取工作台列表
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/27  14:56
     **/
    @RequestMapping("getTowerList")
    @ResponseBody
    public R getTowerList(HttpServletRequest request, HttpServletResponse response) {
        List<Map> towers = tBaseTowerService.getTowerList();
        return R.ok().put("towers", towers);
    }


    @RequestMapping(
            value = {"logout"},
            method = {RequestMethod.GET}
    )
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }

    /**
     * @method_name: saveSelectTowersToSession
     * @describe: 保存已选工作台到session中，方便获取
     * @param: [request, mapList]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/28  19:22
     **/
    @ResponseBody
    @RequestMapping("/saveSelectTowersToSession")
    public R saveSelectTowersToSession(HttpServletRequest request, @RequestBody List<Map> mapList) {
        try {
            HttpSession session = request.getSession();
            SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
            //接种台	2
            //儿保台	3
            //预检台	4
            //登记台	1
            for (int i = 0; i < mapList.size(); i++) {
                Map map = mapList.get(i);
                if (map.get("tower_name").toString().contains("登记")) {
                    session.setAttribute("GLOBAL_REGISTERID", map.get("id"));//工作台编码
                    session.setAttribute("GLOBAL_REGISTERTOWER_NAME", map.get("tower_name"));//工作台名称
                    session.setAttribute("GLOBAL_REGISTERTOWER_TYPE", Constant.TOWER_TYPE_REGISTER);//工作台类型
                    sysUserEntity.setRegisterTowerId(map.get("id").toString());
                    sysUserEntity.setRegisterTowerName(map.get("tower_name").toString());
                    sysUserEntity.setRegisterTowerType(Constant.TOWER_TYPE_REGISTER);
                } else if (map.get("tower_name").toString().contains("接种")) {
                    session.setAttribute("GLOBAL_INOCULATEID", map.get("id"));//工作台编码
                    session.setAttribute("GLOBAL_INOCULATETOWER_NAME", map.get("tower_name"));//工作台名称
                    session.setAttribute("GLOBAL_INOCULATETOWER_TYPE", Constant.TOWER_TYPE_INOCULATE);//工作台类型
                    sysUserEntity.setInoculateTowerId(map.get("id").toString());
                    sysUserEntity.setInoculateTowerName(map.get("tower_name").toString());
                    sysUserEntity.setInoculateTowerType(Constant.TOWER_TYPE_INOCULATE);
                } else if (map.get("tower_name").toString().contains("儿保")) {
                    session.setAttribute("GLOBAL_CHILDPROTECTIONID", map.get("id"));//工作台编码
                    session.setAttribute("GLOBAL_CHILDPROTECTIONTOWER_NAME", map.get("tower_name"));//工作台名称
                    session.setAttribute("GLOBAL_CHILDPROTECTIONTOWER_TYPE", Constant.TOWER_TYPE_CHILD_HEALTHCARE);//工作台类型
                    sysUserEntity.setChildProtectionTowerId(map.get("id").toString());
                    sysUserEntity.setChildProtectionTowerName(map.get("tower_name").toString());
                    sysUserEntity.setChildProtectionTowerType(Constant.TOWER_TYPE_CHILD_HEALTHCARE);
                } else if (map.get("tower_name").toString().contains("预检")) {
                    session.setAttribute("GLOBAL_PRECHECKID", map.get("id"));//工作台编码
                    session.setAttribute("GLOBAL_PRECHECKTOWER_NAME", map.get("tower_name"));//工作台名称
                    session.setAttribute("GLOBAL_PRECHECKTOWER_TYPE", Constant.TOWER_TYPE_CHILD_CHECK);//工作台类型
                    sysUserEntity.setPreCheckId(map.get("id").toString());
                    sysUserEntity.setPreCheckName(map.get("tower_name").toString());
                    sysUserEntity.setPreCheckType(Constant.TOWER_TYPE_CHILD_CHECK);
                }
                session.setAttribute("SYS_USER_INFO", sysUserEntity);
            }
            return R.ok().put("sysUserEntity", sysUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("选台保存异常，请联系管理员");
        }
    }


    /**
     * @method_name: getLoginUserRole
     * @describe: 获取登录用户的角色，判断是否显示工作台
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/29  10:25
     **/
    @RequestMapping("getLoginUserRole")
    @ResponseBody
    public R getLoginUserRole(HttpServletRequest request, HttpServletResponse response) {
        SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
        List<String> list = sysUserService.getLoginUserRoleName(sysUserEntity.getUserId());
        if (sysUserEntity.getRegisterTowerId() != null || sysUserEntity.getInoculateTowerId() != null
                || sysUserEntity.getChildProtectionTowerId() != null || sysUserEntity.getPreCheckId() != null) {
            return R.error("已经选择了工作台，不必再选");
        }
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            if (s.equals("机构管理员") || s.equals("超级管理员")||s.equals("村医")) {
                iter.remove();
            }
        }
        if (list.size() > 0) {
            return R.ok(String.valueOf(list.size()));
        } else {
            return R.error("超级管理员或者机构管理员，不需要选择工作台");
        }
    }


    /**
     * @method_name: getLoginUserInfo
     * @describe: 获取登录用户的基本信息
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/29  11:42
     **/
    @RequestMapping("getLoginUserInfo")
    @ResponseBody
    public R getLoginUserInfo(HttpServletRequest request, HttpServletResponse response) {
        SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
        String versionName = "";
        try{
            versionName = PropertiesUtils.getProperty("version.properties", "version.name");
        }catch (Exception e){}
        return R.ok().put("user", sysUserEntity).put("versionName",versionName);
    }

    /**
     *  单点登录
     * @param url
     * @return
     */
    @RequestMapping("sys/login/singleSignOnSSO")
    public void singleSignOnSSO(HttpServletResponse resp, String url) throws IOException {
        if (StringUtils.isBlank(url)) {
            url = PropertiesUtils.getMapValue("SSO.URL");
        }
        try {
            SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
            if (sysUserEntity == null) {
                resp.sendRedirect("redirect:login.html");
                return;
            }

            JSONObject json = new JSONObject();
            json.put("name", sysUserEntity.getUsername());
            json.put("cipher", sysUserEntity.getPassword());

            JSONObject json1 = new JSONObject();
            json1.put("value", json);

            String value = UrlUtils.urlEncode(json1.toJSONString());
            url += "/sys/login/singleSign-on?login="+value;
        } catch (IOException e) {
            e.printStackTrace();
            resp.sendRedirect("redirect:login.html");
            return;
        }
        resp.sendRedirect(url);
    }


    /*@RequestMapping("/sys/login/singleSignOnSSO")
    public void singleSignOnSSO(HttpServletRequest req, HttpServletResponse resp,String url) throws IOException {

        if(StringUtils.isBlank(url)){
            url = PropertiesUtils.getMapValue("SSO.URL");
        }
        SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
        if(sysUserEntity == null){
            ShiroUtils.logout();
            resp.sendRedirect("redirect:login.html");
            // return "redirect:login.html";
        }else{

            try {
                JSONObject json = new JSONObject();
                json.put("name",sysUserEntity.getUsername());
                //json.put("cipher",DESUtils.des3EncodeECB(sysUserEntity.getUserId().getBytes("UTF-8"),sysUserEntity.getPassword().getBytes("UTF-8")));
                json.put("cipher",sysUserEntity.getPassword());
                json.put("date",new Date().getTime());
*//*
            String str = json.toJSONString();
            byte[] conter = new BASE64Decoder().decodeBuffer(str);
            byte[] key = new BASE64Decoder().decodeBuffer(PropertiesUtils.getMapValue("key"));
            byte[] encrypt = DESUtils.des3EncodeECB(key,conter);
            byte[] qm = DESUtils.des3DecodeECB(key,encrypt);

            String content = new String(qm, "UTF-8");

            JSONObject cccc = JSONObject.parseObject(content);

            String value1 = new BASE64Encoder().encode(encrypt);
            String md5 = DigestUtils.md5DigestAsHex(value1.getBytes("UTF-8"));*//*
                JSONObject json2 = new JSONObject();
                json2.put("value",json);
                // json2.put("encrypt",md5);

                String value = UrlUtils.urlEncode(json2.toJSONString(),"UTF-8");
                url += "/singleSign-on?login="+ value;
                System.out.println("访问地址："+url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(url);
        }}*/
}
