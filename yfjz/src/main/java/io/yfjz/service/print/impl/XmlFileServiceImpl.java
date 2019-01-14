package io.yfjz.service.print.impl;


import io.yfjz.dao.print.TChildInfoPrintPointDao;
import io.yfjz.dao.print.TChildPrintModelDao;
import io.yfjz.dao.print.TChildPrintModelPointDao;
import io.yfjz.entity.print.ChildInfoBean;
import io.yfjz.entity.print.InoculateDetailBean;
import io.yfjz.service.print.XmlFileService;
import io.yfjz.utils.R;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
@Service("xmlFileService")
public class XmlFileServiceImpl implements XmlFileService {

    protected static final Logger LOGGER = Logger.getLogger(XmlFileServiceImpl.class);
    private Logger log = Logger.getLogger(XmlFileServiceImpl.class);
    @Autowired
    private TChildPrintModelPointDao tChildPrintModelPointDao;
    @Autowired
    private TChildInfoPrintPointDao tChildInfoPrintPointDao;
    @Autowired
    private TChildPrintModelDao tChildPrintModelDao;

    /**
     * 加载xml文件并返回文档对象
     *
     * @param filename
     * @return Document
     */

    public Document load(String filename){
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(new File(filename)); //读取XML文件,获得document对象
            log.debug("Read Xml Successful");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return document;
    }

    /**
     * 根据传入的修改对象集合来修改xml文件
     * @param filename xml文件路径
     * @param idbs 需要修改的对象集合
     * @return  如果xml文件不存在返回-404 如果保存失败返回-1 正常返回0
     */
    @Override
    public int modifyXml(String filename,List<InoculateDetailBean> idbs) {
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(new File(filename)); //读取XML文件,获得document对象
        } catch (Exception e) {
            e.printStackTrace();
            return -404;
        }
        Element rootElement = document.getRootElement();
        List<Element> classes = rootElement.selectNodes("//bacterinclass");//获取所有的bacterinclass节点
        for (InoculateDetailBean idb : idbs) {
            for (Element cls : classes) {
                Element el = cls.element("bacterinclasspattern");//获取bacterinclass节点下的bacterinclasspattern节点对象
                final Pattern compile = Pattern.compile(el.getText());//获取bacterinclasspattern的值并进行正则编译
                if (compile.matcher(idb.getBacterinClassName()).matches()) { //如果找到修改的对象中符合正则表达则开始修改内容，否则不做任何处理
                    //cls.element("page").setText(String.valueOf(idb.getPage()));//修改页码
                    List<Element> datas = cls.element("showdatas").elements();
                    for (Element el2 : datas) {
                        final String name = el2.element("name").getText();
                        switch (name) {
                            case "inoculate_date": {
                                el2.element("x").setText(String.valueOf(idb.getInoculate_dateX())); //修改接种日期的位置
                                el2.element("y").setText(String.valueOf(idb.getInoculate_dateY()));
                                break;
                            }
                            case "text": {
                                el2.element("x").setText(String.valueOf(idb.getTextX()));//修改接种部位的位置
                                el2.element("y").setText(String.valueOf(idb.getTextY()));
                                break;
                            }
                            case "batchnum": {
                                el2.element("x").setText(String.valueOf(idb.getBatchnumX()));//修改疫苗批号的位置
                                el2.element("y").setText(String.valueOf(idb.getBatchnumY()));
                                break;
                            }
                            case "factory_name": {
                                el2.element("x").setText(String.valueOf(idb.getFactory_nameX()));//修改生产厂家的位置
                                el2.element("y").setText(String.valueOf(idb.getFactory_nameY()));
                                break;
                            }
                            case "departname": {
                                el2.element("x").setText(String.valueOf(idb.getDepartnameX()));//修改接种单位的位置
                                el2.element("y").setText(String.valueOf(idb.getDepartnameY()));
                                break;
                            }
                            case "doctor": {
                                el2.element("x").setText(String.valueOf(idb.getDoctorX()));//修改接种单位的位置
                                el2.element("y").setText(String.valueOf(idb.getDoctorY()));
                                break;
                            }
                            case "expiration_date": {
                                el2.element("x").setText(String.valueOf(idb.getExpiration_dateX()));//修改接种单位的位置
                                el2.element("y").setText(String.valueOf(idb.getExpiration_dateY()));
                                break;
                            }
                        }
                    }
                }
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            try {
                // 创建XMLWriter对象
                XMLWriter writer = new XMLWriter(new FileOutputStream(filename), format);
                //设置不自动进行转义
                writer.setEscapeText(false);
                // 生成XML文件
                writer.write(document);
                //关闭XMLWriter对象
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    /**
     * 根据传入的修改对象集合来修改xml文件
     * @param filename xml文件路径
     * @param idbs 需要修改的对象集合
     * @return  如果xml文件不存在返回-404 如果保存失败返回-1 正常返回0
     */
    @Override
    public int modifyJrxml(String filename,List<ChildInfoBean> idbs) {
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {

            FileInputStream fis = new FileInputStream(new File(filename));
            //document = saxReader.read(new File(filename)); //读取XML文件,获得document对象
            document = saxReader.read(fis); //读取XML文件,获得document对象

        } catch (Exception e) {
            e.printStackTrace();
            return -404;
        }
        Element rootElement = document.getRootElement();
        Element title = rootElement.element("title");
        Element band = title.element("band");//获取所有的bacterinclass节点
        //获取 jrxml 文件中任意深度的 <parameter> 元素的一个实例即是：获取该 xml 文件中的所有的 <parameter> 元素，同样对于其中所有的 <field> 元素也是可以的。根据上面 Dom4j 理解的 XPath 格式，要获取所有 <parameter> 元素的 XPath 应写为：//*[name()='parameter']，类似的，要获取所有 <field> 元素的 XPath 应写为：//*[name()='field']。
        //*[name()='parameter']
        List<Element>  classes = band.selectNodes("//*[name()='textField']"); //获取所有的textField节点
       /* Iterator<?> parameterItr = classes.iterator();
             while(parameterItr.hasNext()) {
                     Element parameterElement = (Element)parameterItr.next();
                     System.out.println("Current parameterElement XPath: " + parameterElement.getPath());
                     Element reportElement  = parameterElement.element("reportElement");       // 获取reportElement元素
                     Element textFieldExpression  = parameterElement.element("textFieldExpression");       // 获取textFieldExpression
                     String x  = reportElement.attributeValue("x");       // parameter x
                     String y = reportElement.attributeValue("y");      // parameter y
                     System.out.println("parameterName = " + x + "; parameterClass = " + y);
                     System.out.println("--------------------------------------------------------------------------");
                 }*/
        for (ChildInfoBean idb : idbs) {
            for (Element cls : classes) {
                //Element parameterElement = (Element)parameterItr.next();
                System.out.println("Current parameterElement XPath: " + cls.getPath());
                Element reportElement  = cls.element("reportElement");       // 获取reportElement元素
                Element textFieldExpression  = cls.element("textFieldExpression");       // 获取textFieldExpression
                String name  = textFieldExpression.getText();      //
                String nameSub  = name.substring(name.indexOf("{")+1,name.indexOf("}"));   //
                if(idb.getProperty().equals(nameSub) || idb.getProperty().equals(nameSub.toLowerCase()) || idb.getProperty().equals(nameSub.toUpperCase())){
                    reportElement.setAttributeValue("x",String.valueOf(idb.getProperty_X())); //修改接种日期的位置
                    reportElement.setAttributeValue("y",String.valueOf(idb.getProperty_Y()));
                }
                String x  = reportElement.attributeValue("x");       // parameter x
                String y = reportElement.attributeValue("y");      // parameter y
                System.out.println("parameterName = " + x + "; parameterClass = " + y);
                System.out.println("--------------------------------------------------------------------------");


                }

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            try {
                // 创建XMLWriter对象
                XMLWriter writer = new XMLWriter(new FileOutputStream(filename), format);
                //设置不自动进行转义
                writer.setEscapeText(false);
                // 生成XML文件
                writer.write(document);
                //关闭XMLWriter对象
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    @Override
    public  int xmlElements(HttpServletResponse res, String xmlDoc, String printType) {

        List list = new ArrayList<String>();
        List<InoculateDetailBean> mapList = new ArrayList<>();
        List<Map<String,Object>> listmap = new ArrayList<>();
       // SAXBuilder sb = new SAXBuilder();
        SAXReader saxReader = new SAXReader();
        //document = saxReader.read(new File(xmlDoc));
        try {

            Document doc =  saxReader.read(new File(xmlDoc));
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            //List jiedian = root.elements();
            List<Element> classes = root.selectNodes("//bacterinclass");// 得到根元素所有子元素的集合


            Element et1 = null;
            Element el = null;
            int inoculate_dateX = 0;
            int inoculate_dateY = 0;
            int textX = 0;
            int textY = 0;
            int batchnumX = 0;
            int batchnumY = 0;
            int factory_nameX = 0;
            int factory_nameY = 0;
            int departnameX = 0;
            int departnameY = 0;
            int doctorX = 0;
            int doctorY = 0;
            int expiration_dateX = 0;
            int expiration_dateY = 0;
            //et1 = (Element)jiedian.get(0);

            //int lines = Integer.parseInt(et1.getChildText("LINES"));

            //System.out.println(lines);
            for(int i = 0; i < classes.size();i++) {
                InoculateDetailBean bean = new InoculateDetailBean();
                 //el = classes.get(i).element("bacterinclasspattern");//获取bacterinclass节点下的bacterinclasspattern节点对象
                el = classes.get(i);
               // final Pattern compile = Pattern.compile(el.getText());//获取bacterinclasspattern的值并进行正则编译
                List<Element> datas = el.element("showdatas").elements();
                String bacterinClassName = el.element("bacterinclasspattern").getText();

                for (Element el2 : datas) {
                    final String name = el2.element("name").getText();
                    switch (name) {
                        case "inoculate_date": {
                             inoculate_dateX = Integer.parseInt(el2.element("x").getText());
                             inoculate_dateY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                        case "text": {
                             textX = Integer.parseInt(el2.element("x").getText());
                             textY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                        case "batchnum": {
                             batchnumX = Integer.parseInt(el2.element("x").getText());
                             batchnumY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                        case "factory_name": {
                             factory_nameX = Integer.parseInt(el2.element("x").getText());
                             factory_nameY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                        case "departname": {
                             departnameX = Integer.parseInt(el2.element("x").getText());
                             departnameY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                        case "doctor": {
                             doctorX = Integer.parseInt(el2.element("x").getText());
                             doctorY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                        case "expiration_date": {
                            expiration_dateX = Integer.parseInt(el2.element("x").getText());
                            expiration_dateY = Integer.parseInt(el2.element("y").getText());
                            break;
                        }
                    }

            }
                bean.setBacterinClassName(bacterinClassName);
                bean.setInoculate_dateX(inoculate_dateX);
                bean.setInoculate_dateY(inoculate_dateY);
                bean.setTextX(textX);
                bean.setTextY(textY);
                bean.setBatchnumX(batchnumX);
                bean.setBatchnumY(batchnumY);
                bean.setFactory_nameX(factory_nameX);
                bean.setFactory_nameY(factory_nameY);
                bean.setDepartnameX(departnameX);
                bean.setDepartnameY(departnameY);
                bean.setDoctorX(doctorX);
                bean.setDoctorY(doctorY);
                bean.setExpiration_dateX(expiration_dateX);
                bean.setExpiration_dateY(expiration_dateY);

                mapList.add(bean);
                //retmap.put("",bean);

            }


        } catch (Exception e) {

            e.printStackTrace();

        }
        int rows = 0;
        for(InoculateDetailBean bean:mapList){
            Map<String, Object> retmap = new HashMap<String, Object>();
            retmap.put("bio_name",bean.getBacterinClassName());
            retmap.put("inoculate_dateX",bean.getInoculate_dateX());
            retmap.put("inoculate_dateY",bean.getInoculate_dateY());
            retmap.put("textX",bean.getTextX());
            retmap.put("textY",bean.getTextY());
            retmap.put("batchnumX",bean.getBatchnumX());
            retmap.put("batchnumY",bean.getBatchnumY());
            retmap.put("factory_nameX",bean.getFactory_nameX());
            retmap.put("factory_nameY",bean.getFactory_nameY());
            retmap.put("departnameX",bean.getDepartnameX());
            retmap.put("departnameY",bean.getDepartnameY());
            retmap.put("doctorX",bean.getDoctorX());
            retmap.put("doctorY",bean.getDoctorY());
            retmap.put("expiration_dateX",bean.getExpiration_dateX());
            retmap.put("expiration_dateY",bean.getExpiration_dateY());
            retmap.put("printType",printType);
            //listmap.add(retmap);
            try{
                rows = tChildPrintModelPointDao.update(retmap);
                if(rows<=0){
//                    System.out.print(rows);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return 0;

    }
    @Override
    public  int jrxmlElements(HttpServletResponse res, String xmlDoc,String printType) {

        List list = new ArrayList<String>();
        List<ChildInfoBean> mapList = new ArrayList<>();
        List<Map<String,Object>> listmap = new ArrayList<>();
        // SAXBuilder sb = new SAXBuilder();
        SAXReader saxReader = new SAXReader();
        //document = saxReader.read(new File(xmlDoc));
        try {

            Document doc =  saxReader.read(new File(xmlDoc));
            Element root = doc.getRootElement();
//            System.out.println(root.getName());//输出根元素的名称（测试）
            //List jiedian = root.elements();
            //Element rootElement = document.getRootElement();
            Element title = root.element("title");
            Element band = title.element("band");//获取所有的bacterinclass节点
            //获取 jrxml 文件中任意深度的 <parameter> 元素的一个实例即是：获取该 xml 文件中的所有的 <parameter> 元素，同样对于其中所有的 <field> 元素也是可以的。根据上面 Dom4j 理解的 XPath 格式，要获取所有 <parameter> 元素的 XPath 应写为：//*[name()='parameter']，类似的，要获取所有 <field> 元素的 XPath 应写为：//*[name()='field']。
            //*[name()='parameter']
            List<Element>  classes = band.selectNodes("//*[name()='textField']"); //获取所有的textField节点

            Element et1 = null;
            Element el = null;
            String  property = "";

            //System.out.println(lines);
            for(int i = 0; i < classes.size();i++) {
                ChildInfoBean bean = new ChildInfoBean();
                //el = classes.get(i).element("bacterinclasspattern");//获取bacterinclass节点下的bacterinclasspattern节点对象
                el = classes.get(i);
                // final Pattern compile = Pattern.compile(el.getText());//获取bacterinclasspattern的值并进行正则编译
                /*List<Element> datas = el.element("showdatas").elements();
                String bacterinClassName = el.element("bacterinclasspattern").getText();*/
                et1 = el.element("reportElement");
                String childProperty = el.element("textFieldExpression").getText();
                String nameSub  = childProperty.substring(childProperty.indexOf("{")+1,childProperty.indexOf("}"));

                bean.setProperty(nameSub);
                bean.setProperty_X(Integer.valueOf(et1.attributeValue("x")));
                bean.setProperty_Y(Integer.valueOf(et1.attributeValue("y")));
                mapList.add(bean);
                //retmap.put("",bean);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        int rows = 0;
        for(ChildInfoBean bean:mapList){
            Map<String, Object> retmap = new HashMap<String, Object>();
            retmap.put("child_properties",bean.getProperty());
            retmap.put("properties_X",bean.getProperty_X());
            retmap.put("properties_Y",bean.getProperty_Y());
            retmap.put("printType",printType);
            //listmap.add(retmap);
            try{
                rows = tChildInfoPrintPointDao.update(retmap);
                if(rows<=0){
                    System.out.print(rows);
                    return 1;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return 0;

    }
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
    @Override
    public  List<Map<String,Object>> queryInoculateCoordinate(HttpServletResponse res, String printType){
        List<Map<String,Object>> listmap =null;
        Map<String,Object> map = new HashMap<>();
        map.put("printType",printType);
        try{
             listmap = tChildPrintModelPointDao.queryListMap(map);
        }catch (Exception e){
           e.printStackTrace();
        }

       return listmap;
    }
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
    @Override
    public R queryChildInfoCoordinate(HttpServletResponse res, String printType){
        List<Map<String,Object>> listmap =null;
        Map<String,Object> map = new HashMap<>();
        map.put("printType",printType);
        try{
            listmap = tChildInfoPrintPointDao.queryListMap(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject json=new JSONObject();
        if(listmap !=null && listmap.size()>0){
            json.put("listmap", listmap);
            //json.put("inc", inclist);
        }
        return   R.ok(json);
    }

    /**
     * author 饶士培
     * 创建xml配置文件
     * time   2018-07-10 16:38:34
     * @param filename
     * @param idbs
     * @return
     */
    @Override
    public int createXml(String path,String filename,String newfilename,List<InoculateDetailBean> idbs) {
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(new File(filename)); //读取XML文件,获得document对象
        } catch (Exception e) {
            e.printStackTrace();
            return -404;
        }
        Element rootElement = document.getRootElement();
        List<Element> classes = rootElement.selectNodes("//bacterinclass");//获取所有的bacterinclass节点
        for (InoculateDetailBean idb : idbs) {
            for (Element cls : classes) {
                Element el = cls.element("bacterinclasspattern");//获取bacterinclass节点下的bacterinclasspattern节点对象
                final Pattern compile = Pattern.compile(el.getText());//获取bacterinclasspattern的值并进行正则编译
                if (compile.matcher(idb.getBacterinClassName()).matches()) { //如果找到修改的对象中符合正则表达则开始修改内容，否则不做任何处理
                    //cls.element("page").setText(String.valueOf(idb.getPage()));//修改页码
                    List<Element> datas = cls.element("showdatas").elements();
                    for (Element el2 : datas) {
                        final String name = el2.element("name").getText();
                        switch (name) {
                            case "inoculate_date": {
                                el2.element("x").setText(String.valueOf(idb.getInoculate_dateX())); //修改接种日期的位置
                                el2.element("y").setText(String.valueOf(idb.getInoculate_dateY()));
                                break;
                            }
                            case "text": {
                                el2.element("x").setText(String.valueOf(idb.getTextX()));//修改接种部位的位置
                                el2.element("y").setText(String.valueOf(idb.getTextY()));
                                break;
                            }
                            case "batchnum": {
                                el2.element("x").setText(String.valueOf(idb.getBatchnumX()));//修改疫苗批号的位置
                                el2.element("y").setText(String.valueOf(idb.getBatchnumY()));
                                break;
                            }
                            case "factory_name": {
                                el2.element("x").setText(String.valueOf(idb.getFactory_nameX()));//修改生产厂家的位置
                                el2.element("y").setText(String.valueOf(idb.getFactory_nameY()));
                                break;
                            }
                            case "departname": {
                                el2.element("x").setText(String.valueOf(idb.getDepartnameX()));//修改接种单位的位置
                                el2.element("y").setText(String.valueOf(idb.getDepartnameY()));
                                break;
                            }
                            case "doctor": {
                                el2.element("x").setText(String.valueOf(idb.getDoctorX()));//修改接种单位的位置
                                el2.element("y").setText(String.valueOf(idb.getDoctorY()));
                                break;
                            }
                            case "expiration_date": {
                                el2.element("x").setText(String.valueOf(idb.getExpiration_dateX()));//修改接种单位的位置
                                el2.element("y").setText(String.valueOf(idb.getExpiration_dateY()));
                                break;
                            }
                        }
                    }
                }
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            try {
                // 创建XMLWriter对象
                XMLWriter writer = new XMLWriter(new FileOutputStream(path + "WEB-INF/classes/jaserreport/"+newfilename+".xml"), format);
                //设置不自动进行转义
                writer.setEscapeText(false);
                // 生成XML文件
                writer.write(document);
                //关闭XMLWriter对象
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

        }
        return 0;
    }
    @Override
    public int saveModelIntoTable(Map<String,Object> map){
        int result = 0;
        Object obj = map.remove("model_name");
        Object xmlName = map.remove("xml_name");
        map.put("modelName",obj);
        map.put("xmlName",xmlName);

        try {
           tChildPrintModelDao.save(map);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<Map<String,Object>> queryInoculatePrintSetModel(HttpServletResponse res, Map<String,Object> map){
        List<Map<String,Object>> listmap =null;
        try{
            listmap = tChildPrintModelDao.queryListMap(map);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(listmap !=null && listmap.size()>0){
           return listmap;
        }
        return null;

    }

    /**
     * 根据传入的修改对象集合来修改xml文件
     * @param filename xml文件路径
     * @param idbs 需要修改的对象集合
     * @return  如果xml文件不存在返回-404 如果保存失败返回-1 正常返回0
     */
    @Override
    public int createJrxml(String path,String filename,String newfilename,List<ChildInfoBean> idbs) {
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {

            FileInputStream fis = new FileInputStream(new File(filename));
            //document = saxReader.read(new File(filename)); //读取XML文件,获得document对象
            document = saxReader.read(fis); //读取XML文件,获得document对象

        } catch (Exception e) {
            e.printStackTrace();
            return -404;
        }
        Element rootElement = document.getRootElement();
        Element title = rootElement.element("title");
        Element band = title.element("band");//获取所有的bacterinclass节点
        //获取 jrxml 文件中任意深度的 <parameter> 元素的一个实例即是：获取该 xml 文件中的所有的 <parameter> 元素，同样对于其中所有的 <field> 元素也是可以的。根据上面 Dom4j 理解的 XPath 格式，要获取所有 <parameter> 元素的 XPath 应写为：//*[name()='parameter']，类似的，要获取所有 <field> 元素的 XPath 应写为：//*[name()='field']。
        //*[name()='parameter']
        List<Element>  classes = band.selectNodes("//*[name()='textField']"); //获取所有的textField节点
       /* Iterator<?> parameterItr = classes.iterator();
             while(parameterItr.hasNext()) {
                     Element parameterElement = (Element)parameterItr.next();
                     System.out.println("Current parameterElement XPath: " + parameterElement.getPath());
                     Element reportElement  = parameterElement.element("reportElement");       // 获取reportElement元素
                     Element textFieldExpression  = parameterElement.element("textFieldExpression");       // 获取textFieldExpression
                     String x  = reportElement.attributeValue("x");       // parameter x
                     String y = reportElement.attributeValue("y");      // parameter y
                     System.out.println("parameterName = " + x + "; parameterClass = " + y);
                     System.out.println("--------------------------------------------------------------------------");
                 }*/
        for (ChildInfoBean idb : idbs) {
            for (Element cls : classes) {
                //Element parameterElement = (Element)parameterItr.next();
                System.out.println("Current parameterElement XPath: " + cls.getPath());
                Element reportElement  = cls.element("reportElement");       // 获取reportElement元素
                Element textFieldExpression  = cls.element("textFieldExpression");       // 获取textFieldExpression
                String name  = textFieldExpression.getText();      //
                String nameSub  = name.substring(name.indexOf("{")+1,name.indexOf("}"));   //
                if(idb.getProperty().equals(nameSub)){
                    reportElement.setAttributeValue("x",String.valueOf(idb.getProperty_X())); //修改接种日期的位置
                    reportElement.setAttributeValue("y",String.valueOf(idb.getProperty_Y()));
                }
                String x  = reportElement.attributeValue("x");       // parameter x
                String y = reportElement.attributeValue("y");      // parameter y
                System.out.println("parameterName = " + x + "; parameterClass = " + y);
//                System.out.println("--------------------------------------------------------------------------");


            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            try {
                // 创建XMLWriter对象
                XMLWriter writer = new XMLWriter(new FileOutputStream(path+"reports/"+newfilename+".jrxml"), format);
                //设置不自动进行转义
                writer.setEscapeText(false);
                // 生成XML文件
                writer.write(document);
                //关闭XMLWriter对象
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    @Override
    public int deleteModel(Object[] obj){
        int result = 0;
        try{
            result = tChildPrintModelDao.deleteBatch(obj);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;

    }

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
