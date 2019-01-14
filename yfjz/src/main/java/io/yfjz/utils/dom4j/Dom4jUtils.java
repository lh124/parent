package io.yfjz.utils.dom4j;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author 刘琪
 * @describe: dom4j解析xml文件
 * @className: Dom4jUtils
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017-12-01  11:48
 **/
public class Dom4jUtils {

    /**
     * @describe:  从文件读取XML，输入文件名，返回XML文档
     * @method_name: read
     * @param [fileName: 文件的路径]
     * @return org.dom4j.Document
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-01  14:51
     **/
    public static Document getDocument(String fileName) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(fileName));
        return document;
    }

    /**
     * @describe: 获取xml的根元素
     * @method_name: getRootElement
     * @param [doc]
     * @return org.dom4j.Element
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-01  14:51
     **/
    public Element getRootElement(Document doc){
        return doc.getRootElement();
    }

    /**
     * @describe: 创建Document对象，并放回
     * @method_name: createDocument
     * @param []
     * @return org.dom4j.Document
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-01  15:12
     **/
    public static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        Element author1 = root.addElement("author")
                .addAttribute("name", "James")
                .addAttribute("location", "UK")
                .addText("James Strachan");

        Element author2 = root.addElement("author")
                .addAttribute("name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");

        return document;
    }
    
    /**
     * @describe: 读取document中的数据，使用Xpath的方式
     * @method_name: readDocumentByXpath
     * @param [document]
     * @return void
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-01  15:09
     **/
    public static void readDocumentByXpath(Document document) {
        List<Node> list = document.selectNodes("//person/p1");
        for (Node node : list) {
            List s = node.selectNodes("name");
            for (int i = 0; i < s.size(); i++) {
                Element element = (Element) s.get(i);
//                System.out.println(element.getStringValue());
            }
           /* System.out.println("node.getName()>>>"+node.getName());
            System.out.println("node.getNodeType()>>>"+node.getNodeType());
            System.out.println("node.getStringValue()>>>"+node.getStringValue());
            System.out.println("node.getNodeTypeName()>>>"+node.getNodeTypeName());
            System.out.println("node.getParent()>>>"+node.getParent());
            System.out.println("node.getPath()>>>"+node.getPath());
            System.out.println("node.getText()>>>"+node.getText());*/
        }

       /* Node node = document.selectSingleNode("//person/p1/name");

        String name = node.getStringValue();

        System.out.println(name);*/
    }
}