package io.yfjz.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * XML工具类
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-24 11:33:28
 */
public class XmlUtils {

	/**
	 * Document对象 转换成 Map对象
	 * @param document xml文档对象
	 * @return 将xml结构按层级转换的Map
	 */
	public static Map<String, Object> xmlToMap(Document document) throws Exception {
		if (document == null) {
			System.err.println("xmlToMap方法参数为空,方法直接退出,返回值返回null");
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Element rootElement = document.getRootElement();
		element2map(rootElement, map);
		return map;
	}
	
	/**
	 * String字符串对象 转换成 Map对象
	 * @param xmlString 一个xml字符串
	 * @return 将xml结构按层级转换的Map
	 * @throws Exception
	 */
	public static Map<String, Object> xmlStringToMap(String xmlString) throws Exception {
		if (StringUtils.isEmpty(xmlString)) {
			System.err.println("xmlStringToMap方法参数为空或空字符串,方法直接退出,返回值返回null");
			return null;
		}
		
		Document document = stringToXmlDocument(xmlString);
		return xmlToMap(document);
	}
	
	/**
	 * 字节数组 转换成 Map对象
	 * @param rawXmlBytes 一个xml字符串的byte数组
	 * @return 将xml结构按层级转换的Map
	 * @throws Exception
	 */
	public static Map<String, Object> xmlBytesToMap(byte[] rawXmlBytes) throws Exception {
		if (rawXmlBytes == null) {
			System.err.println("xmlBytesToMap方法参数为空,方法直接退出,返回值返回null");
			return null;
		}
		
		return xmlStringToMap(new String(rawXmlBytes, "UTF-8"));
	}

	/**
	 * Element对象 转换成 Map对象
	 * @param element xml的一个元素(通常传递根元素)
	 * @param map 将xml结构按层级转换的Map
	 */
	@SuppressWarnings("unchecked")
	public static void element2map(Element element, Map<String, Object> map) {
		if (null == element) {
			return;
		}
		String name = element.getName();
		if (element.isTextOnly()) { // 如果是文本直接放入map
			map.put(name, element.getText());
		} else { // 如果不是文本继续递归
			Map<String, Object> subElementsMap = new HashMap<String, Object>();
			List<Element> subElements = (List<Element>) element.elements();
			// 递归遍历子元素
			for (Element subElement : subElements) {
				element2map(subElement, subElementsMap);
			}
			// 回溯时处理子元素
			Object first = map.get(name);
			if (null == first) {
				map.put(name, subElementsMap);
			} else {
				if (first instanceof List) {
					((List<Map<String, Object>>) first).add(subElementsMap);
				} else {
					List<Object> subList = new ArrayList<Object>();
					subList.add(first);
					subList.add(subElementsMap);
					map.put(name, subList);
				}
			}
		}
	}

	/**
	 * String字符串对象 转换成 xml的Document对象
	 * @param xmlString
	 * @return xml的Document对象
	 * @throws Exception
	 */
	public static Document stringToXmlDocument(String xmlString) throws Exception {
		SAXReader reader = new SAXReader();
		StringReader stringReader = new StringReader(xmlString);
		System.out.println(xmlString);
		Document document = reader.read(stringReader);
		return document;
	}

	/**
	 * Map对象转换成 xml的Document对象
	 * @param map
	 * @return xml对应的Document对象
	 * @throws DocumentException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Document map2xml(Map<String, Object> map) throws DocumentException, IOException {
		Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
		if (entries.hasNext()) { // 获取第一个键创建根节点
			Map.Entry<String, Object> entry = entries.next();
			Document doc = DocumentHelper.createDocument();
			Element root = DocumentHelper.createElement(entry.getKey());
			doc.add(root);
			map2xml((Map) entry.getValue(), root);
			return doc;
		}
		return null;
	}

	/**
	 * map转xml
	 * @param map
	 * @param body xml元素
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Element map2xml(Map<String, Object> map, Element body) {
		Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.startsWith("@")) { // 属性
				body.addAttribute(key.substring(1, key.length()), value.toString());
			} else if (key.equals("#text")) { // 有属性时的文本
				body.setText(value.toString());
			} else {
				if (value instanceof List) {
					List list = (List) value;
					Object obj;
					for (int i = 0; i < list.size(); i++) {
						obj = list.get(i);
						// list里是map或String，不会存在list里直接是list的，
						if (obj instanceof Map) {
							Element subElement = body.addElement(key);
							map2xml((Map) list.get(i), subElement);
						} else {
							body.addElement(key).setText((String) list.get(i));
						}
					}
				} else if (value instanceof Map) {
					Element subElement = body.addElement(key);
					map2xml((Map) value, subElement);
				} else {
					body.addElement(key).setText(value.toString());
				}
			}
			// System.out.println("Key = " + entry.getKey() + ", Value = " +
			// entry.getValue());
		}
		return body;
	}

	/**
	 * 格式化输出xml字符串
	 * @param xmlStr 一个xml字符串
	 * @return 格式化过后的xml字符串
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXml(String xmlStr) throws DocumentException, IOException {
		Document document = DocumentHelper.parseText(xmlStr);
		return formatXml(document);
	}

	/**
	 * 格式化输出xml
	 * @param document xml的Document对象
	 * @return 格式化过后的xml字符串
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXml(Document document) throws DocumentException, IOException {
		// 格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		// 格式化输出流
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		// 将document写入到输出流
		xmlWriter.write(document);
		xmlWriter.close();
		//return new String(writer.toString().getBytes("UTF-8"),"UTF-8");
		//return new String(writer.toString());
		return writer.toString();
	}

	/**
	 * 将xml字符串转换字节数组，改字节数组是经过ZipOutputStream压缩处理的
	 * @param xmlString 一个xml字符串
	 * @return 压缩xml字符串的字节数组过后的字节数组
	 * @throws Exception
	 */
	public static byte[] zipBytes(String xmlString, String zipEntryName) throws Exception {
		Document document = DocumentHelper.parseText(xmlString);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format = OutputFormat.createCompactFormat();
		StringWriter sw = new StringWriter();
		XMLWriter writer = new XMLWriter(sw, format);
		writer.write(document);
		writer.close();
		byte[] xmlBytes = sw.toString().getBytes("UTF-8");
		byte[] zippedBytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos);
		ZipEntry entry = new ZipEntry(zipEntryName);
		entry.setSize(xmlBytes.length);
		zos.putNextEntry(entry);
		zos.write(xmlBytes);
		zos.closeEntry();
		zos.close();
		zippedBytes = bos.toByteArray();
		bos.close();

		return zippedBytes;
	}
	
	/**
	 * 对压缩过的xml字节数组进行Zip解压
	 * @param zipResult 一个.zip的字节数组
	 * @return 解压后的字节数组
	 */
	public static byte[] unzipBytes(byte[] zipResult) {
		if (zipResult == null) {
			System.err.println("byte数组为空，放弃解压!");
			return null;
		}
		byte[] unzippedBytes = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(zipResult);
		ZipInputStream zis = new ZipInputStream(bis);
		try {
			while (zis.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zis.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				unzippedBytes = baos.toByteArray();

				baos.flush();
				baos.close();
			}
			zis.close();
			bis.close();
		} catch (IOException e) {
			System.err.println("解压文件出错: " + e.getMessage());
		}
		return unzippedBytes;
	}
}

