package io.yfjz.controller.bus;


import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.service.bus.ChildService;
import io.yfjz.utils.R;
import io.yfjz.utils.page.PageBean;
import io.yfjz.utils.page.PageParam;
import io.yfjz.utils.page.RequestPageParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 16:46 2018/09/29
 */
@Controller
@RequestMapping("child")
public class ChildController {

    @Autowired
    private ChildService childService;

    /**儿童重复筛选列表*/
    @RequestMapping("/listDataChildFilter")
    @ResponseBody
    public PageBean<List<Map<String,Object>>> listDataChildFilter(@RequestPageParam PageParam pageParam, TChildInfoEntity tChildInfoEntity){
        tChildInfoEntity.setChilBirthday(new Date());
        PageBean<List<Map<String,Object>>> list = childService.listDataChild(tChildInfoEntity,pageParam);
        return list;
    }

    /**
     *
     *  根据儿童编码获取接种信息
     *  @author 张羽丰
     * @return 返回每个对应儿童编码所有的接种记录
     */
    @RequestMapping("/getInoculateInfoByChildId")
    @ResponseBody
    public R getInoculateInfoByChildId(@RequestParam Map<String,Object> child){
        Map<String,Object> map = childService.getInoculateInfoByChildId(child);
        return R.ok().put("data",map);
    }

    /**
     * 合并重复儿童的基本信息，接种记录
     * @return
     */
    @RequestMapping("/margeInoculate")
    @ResponseBody
    public R margeInoculate(String param){


        //List<Map> parseArray = com.alibaba.fastjson.JSONObject.parseArray(param, Map.class);
        //List<Map<String,Object>> list = JSONArray.toList(JSONArray.fromObject(param),new HashMap<String,Object>(),new JsonConfig());
        childService.margeInoculate(param);
        return R.ok();
    }
}
