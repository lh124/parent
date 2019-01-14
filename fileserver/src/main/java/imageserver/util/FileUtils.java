package imageserver.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @describe: 文件转换工具类
 * class_name: FileUtils
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017-11-30  17:34
 **/
public class FileUtils {
	private static Logger logger = Logger.getLogger(FileUtils.class);

	/**
	 * @describe: 创建文件 根据文件路径，创建带目录的File文件 说明：创建文件目录是没有异常的情况，只有创建文件才有IO异常
	 * @method_name: createFile
	 * @param [filePath] 此参数是需要创建文件的全路径，否则报错
	 * @return java.io.File
	 * @author 刘琪
	 * @QQ：1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2017-11-30  17:35
	 **/
	public static File createFile(String filePath) {
		File f = null;
		try {
			int pos = filePath.lastIndexOf("/") + 1;
			if (pos == 0) {
				logger.info("传递的文件路径不是全路径，请检查参数路径 ...");
				return null;
			}
			File directory = new File(filePath.substring(0, pos));// 文件目录路径
			f = new File(filePath);
			if (!directory.exists()) {
				directory.mkdir();// 创建目录，如果文件目录不存在
			}
			if (!f.exists()) {
				f.createNewFile();// 创建文件，如果文件不存在
			}
		} catch (IOException e) {
			logger.info("文件创建出现IO异常 ...");
			e.printStackTrace();
		} finally {
			logger.info("执行结束 ...");
		}
		return f;

	}

	/**
	 * @describe: 获取文件的随机名称
	 * @method_name: getRandomfileName
	 * @param []
	 * @return java.lang.String
	 * @author 刘琪
	 * @QQ：1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2017-11-30  17:35
	 **/
	public static String getRandomfileName() {
		StringBuffer sb = null;
		try {
			sb = new StringBuffer();
			DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
			sb.append(df.format(new Date()));
			String filename = RandomStringUtils.random(4, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
					'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
			sb.append(filename);
			logger.info("执行获取文件的随机名称 ：" + sb.toString());
		} catch (Exception e) {
			logger.info("生成随机文件名称异常 ...");
			e.printStackTrace();
		} finally {
			logger.info("执行生成随机文件名称结束 ...");
		}
		return sb.toString();
	}

	/**
	 * @describe:检查文件格式
	 * @method_name: checkFileType
	 * @param [typeArr 允许上传的类型, fileType 文件类型]
	 * @return boolean
	 * @author 刘琪
	 * @QQ：1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2017-11-30  17:36
	 **/
	public static boolean checkFileType(String[] typeArr, String fileType) {
		for (String key : typeArr) {
			if (key.equals(fileType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @describe: 读取文本文件 从File文件中读取内容 说明:读取文件必须存在
	 * @method_name: readFile
	 * @param [f] 读取的文件
	 * @return java.lang.String
	 * @author 刘琪
	 * @QQ：1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2017-11-30  17:36
	 **/
	public static String readFile(File f) {
		BufferedReader br = null;
		try {
			logger.info("进入文件读取 ...");
			br = new BufferedReader(new FileReader(f));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (IOException e) {
			logger.info("读取文件内容出现IO异常...");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.info("关闭流出现BufferedReader  IO异常 ...");
					e.printStackTrace();
				}
			}
			logger.info("读取文件完成 ...");
		}
		return null;
	}

	/**
	 * @describe: 写入文件 PrintWriter，BufferedWriter 速度测试过成功，BufferedWriter速度要比PrintWriter速度快得多，性能更优 但是写的时候是buty[]来写
	 * @method_name: writeFile
	 * @param [f 需要写入的文件, content 需要写入的内容字符串]
	 * @return void
	 * @author 刘琪
	 * @QQ：1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2017-11-30  17:37
	 **/
	public static void writeFile(File f, String content) {
		BufferedWriter bw = null;
		try {
			logger.info("进入文件写入 ...");
			bw = new BufferedWriter(new FileWriter(f));
			bw.write(content.toCharArray());// 大数据量是，使用byte[]的方式来写，速度会提升，性能更好
			bw.flush();
		} catch (IOException e) {
			logger.info("写入文件出现IO异常 ...");
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("写入文件完成 ...");
		}
	}

	/**
	 * @describe: 文件拷贝 说明：使用文件管道的方式实现文件的对拷
	 * @method_name: copyFileByChannel
	 * @param [f1, f2]
	 * @return void
	 * @author 刘琪
	 * @QQ：1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2017-11-30  17:37
	 **/
	public static void copyFileByChannel(File f1, File f2) {
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel inC = null;
		FileChannel outC = null;
		try {
			logger.info("进入文件拷贝 ...");
			in = new FileInputStream(f1);
			out = new FileOutputStream(f2);
			inC = in.getChannel();
			outC = out.getChannel();
			int length;
			int bufferSize = 2 * 1024 * 1024;// 缓冲大小10M
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					break;// FileChannel关闭以后，while循环还在执行所以就抛出异常了。
				}
				if ((inC.size() - inC.position()) < bufferSize) {
					length = (int) (inC.size() - inC.position());
				} else {
					length = bufferSize;
				}
				inC.transferTo(inC.position(), length, outC);
				inC.position(inC.position() + length);
			}
		} catch (IOException e) {
			logger.info("文件拷贝出现IO异常 ...");
			e.printStackTrace();
		} finally {

			if (outC != null) {
				try {
					outC.close();
				} catch (IOException e) {
					logger.info("关闭流出现FileChannel outC IO异常 ...");
					e.printStackTrace();
				}
			}
			if (inC != null) {
				try {
					inC.close();
				} catch (IOException e) {
					logger.info("关闭流出现FileChannel inC IO异常 ...");
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.info("关闭流出现FileOutputStream IO异常 ...");
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.info("关闭流出现FileInputStream  IO异常 ...");
					e.printStackTrace();
				}
			}
			logger.info("拷贝完成 ...");
		}
	}

}
