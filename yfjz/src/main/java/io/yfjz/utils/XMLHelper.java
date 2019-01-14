package io.yfjz.utils;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sun.rmi.log.LogHandler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLHelper {
	
	Logger log= Logger.getLogger(LogHandler.class);

	Document document = null;
	String filename=null;
	
	public XMLHelper(){
	
	}
	
	public XMLHelper(String filename){
		this.filename=filename;
		load(filename);
	}
	public  Document load(String filename) {
		try {
			SAXReader saxReader =new SAXReader();
			document = saxReader.read(new File(filename)); //读取XML文件,获得document对象
		}
		catch(Exception e){
		}
        return document;
    }
	
	public List<String> getMainTableList(){
		//E:\Java\xz\src\main\resources\config
		URL url=Thread.currentThread().getContextClassLoader().getResource("/config/main-table.xml");
		load(url.getFile());
		//load("E:\\Java\\xz\\src\\main\\resources\\config\\main-table.xml");
		Element root = document.getRootElement();
		List<Element> list=root.selectSingleNode("main").selectNodes("table");
		List<String> result=new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			Element el=list.get(i);
			result.add(el.getText());
		}
		//System.out.println(result);
		return result;
	}

	public String[] updateConfig(String classname,String id,Map<String,Object> map){
		URL url=Thread.currentThread().getContextClassLoader().getResource("/config/SqlMapper.xml");
		filename=url.getFile();
		load(filename);
		Boolean exist=false;
		Boolean existAttr=false;
		Element root = document.getRootElement();
		List<Element> nlist=root.selectSingleNode("mappers").selectNodes("mapper");
		int n=nlist.size();
		String path=null;
        for (int i=0;i<nlist.size();i++)
        {
            Element e1 = nlist.get(i);
            String resource=e1.attributeValue("resource");
            if(resource.indexOf("/"+classname+".xml")>0){
            	path=resource;
            	break;
            }
        }
        url=Thread.currentThread().getContextClassLoader().getResource("/"+path);
        filename=url.getFile();
		//Log.info("url===="+url);
		load(filename);
         root = document.getRootElement();
//		System.out.println("n========="+root.attributeValue("namespace"));
		 nlist=root.selectNodes("select");
		 path=null;
		 String sql1=null;
        for (int i=0;i<nlist.size();i++)
        {
        	Element e1 = nlist.get(i);
        	if(e1.attributeValue("id").equalsIgnoreCase("selectExcel")){
        		Pattern r = Pattern.compile("(\\S|\\s)+(?=\\sfrom)");
            	String sql=e1.getText();
            	 Matcher m=r.matcher(sql);
            	 if (m.find( )) {
//                     System.out.println("Found value: " + m.group());
                     sql1=m.group().replace("select ", "");
                  }
            	 break;
        	}
        }
        String[] item=sql1.split(",");
        return item;
   }
	
	public String[] test(){
		load("D:/tomcat8/webapps/jxdy/WEB-INF/classes/com/jjxx/mappering/index/Test3.xml");
		Element root = document.getRootElement();
//		System.out.println("n========="+root.attributeValue("namespace"));
		List<Element> nlist=root.selectNodes("select");
		String path=null;
		 String sql1=null;
        for (int i=0;i<nlist.size();i++)
        {
        	Element e1 = nlist.get(i);
        	if(e1.attributeValue("id").equalsIgnoreCase("selectExcel")){
        		Pattern r = Pattern.compile("(\\S|\\s)+(?=\\sfrom)");
            	String sql=e1.getText();
            	 Matcher m=r.matcher(sql);
            	 if (m.find( )) {
//                     System.out.println("Found value: " + m.group());
                     sql1=m.group().replace("select ", "");
                  }
            	 break;
        	}
        }
        String[] item=sql1.split(",");
        return item;
	}
	
	public void test1(){
		//Pattern r = Pattern.compile("(?=(\\,)|(select\\s))(\\w)+(?=(\\,)|(\\sfrom))");
		//Pattern r = Pattern.compile("(?=\\,{1}|\\s+)\\w+(?=\\,{1}|\\s+)");
		Pattern r = Pattern.compile("(\\S|\\s)+(?=\\sfrom)");
    	String sql="select id,name,age,cnt,birthday from test03";
    	 Matcher m=r.matcher(sql);
    	 String sql1=null;
    	 if (m.find( )) {
//             System.out.println("Found value: " + m.group());
             sql1=m.group().replace("select ", "");
//             System.out.println("Found value: " + sql1);
         }
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("com/jjxx/mappering/ld/LdUnit.xml".indexOf("/LdUnit.xml"));
		/*Map<String,Object> map=new HashMap<String,Object>();
    	map.put("test", "����");*/
		XMLHelper xhp=new XMLHelper();
		List<String> list=xhp.getMainTableList();
//		System.out.println(list.get(0));
	}
}
