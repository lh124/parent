package io.yfjz.service.print;


import io.yfjz.entity.print.ChildInfoBean;
import io.yfjz.entity.print.InoculateDetailBean;
import io.yfjz.utils.R;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 这个类为可调式接种证服务类，专门修改InoculateCardPrinterModel.xml这个文件的内容。
 * 需要和InoculateDetailBean这个类一起使用
 * @Author smile
 * @Date 20180317 19:48
 * @Address 贵阳花果园大街贵阳国际中心2号楼
 */
public interface XmlFileService {



    public int modifyXml(String filename,List<InoculateDetailBean> idbs);

    /**
     * 根据传入的修改对象集合来修改xml文件
     * @param filename xml文件路径
     * @param idbs 需要修改的对象集合
     * @return  如果xml文件不存在返回-404 如果保存失败返回-1 正常返回0
     */
    public int modifyJrxml(String filename,List<ChildInfoBean> idbs);


    public  int xmlElements(HttpServletResponse res, String xmlDoc, String printType);

    public  int jrxmlElements(HttpServletResponse res, String xmlDoc,String printType) ;

/**
 * @method_name: queryInoculateCoordinate
 * @describe: 查询疫苗坐标显示到页面
 * @param res, printType]
 * @return void
 * @author 饶士培
 * @QQ: 1013147559@qq.com
 * @tel:18798010686
 * @date: 2018-05-18  10:01
 **/
    public List<Map<String,Object>> queryInoculateCoordinate(HttpServletResponse res, String printType);
/**
 * @method_name: queryChildInfoCoordinate
 * @describe:查询儿童信息坐标显示到页面
 * @param res, printType]
 * @return void
 * @author 饶士培
 * @QQ: 1013147559@qq.com
 * @tel:18798010686
 * @date: 2018-05-18  10:02
 **/
    public R queryChildInfoCoordinate(HttpServletResponse res, String printType);

    /**
     * author 饶士培
     * 创建xml配置文件
     * time   2018-07-10 16:38:34
     * @param filename
     * @param idbs
     * @return
     */
    public int createXml(String path,String filename,String newfilename,List<InoculateDetailBean> idbs);

    public int saveModelIntoTable(Map<String,Object> map);

    public List<Map<String,Object>> queryInoculatePrintSetModel(HttpServletResponse res, Map<String,Object> map);

    /**
     * 根据传入的修改对象集合来修改xml文件
     * @param filename xml文件路径
     * @param idbs 需要修改的对象集合
     * @return  如果xml文件不存在返回-404 如果保存失败返回-1 正常返回0
     */
    public int createJrxml(String path,String filename,String newfilename,List<ChildInfoBean> idbs) ;

    public int deleteModel(Object[] obj);

    /**
     * 测试用的主方法
     * @param args
     */
    /*public static void main(String[] args) {
        XmlFileService xmlFileService = new XmlFileService();
        List<InoculateDetailBean> all = new ArrayList<>(18);
        InoculateDetailBean idb = new InoculateDetailBean();
        idb.setBacterinClassName("乙脑减毒活疫苗");
        idb.setDepartnameY(152);
        idb.setBatchnumY(9082222);
        idb.setPage(12);
        all.add(idb);
        xmlFileService.modifyXml("D:\\javaworkplace\\20171229\\yfjz\\src\\main\\resources\\jaserreport\\InoculateCardPrinterModel.xml",all);
    }*/
}
