package io.yfjz.utils.encrypt;

import io.yfjz.utils.RRException;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @class_name: HashUtils
 * @describe: 文件哈希散列值计算
 * @author: 刘琪
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018/1/5  9:52
 **/
public class HashUtils {


    /**
     * @method_name: SHA256HashCode
     * @describe: 计算文件的哈希散列值
     * @param: [fileName：文件名]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/5  9:49
     **/
    public static String SHA256HashCode(String fileName) {
        try {
            return SHA256HashCode(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RRException("指定文件不存在");
        }
    }
    /**
     * @method_name: SHA256HashCode
     * @describe: 计算文件的哈希散列值
     * @param: [file]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/5  9:49
     **/
    public static String SHA256HashCode(File file) {
        try {
            return SHA256HashCode(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RRException("指定文件不存在");
        }
    }

    /**
     * @method_name: SHA256HashCode
     * @describe: 计算文件的hashcode散列码，用来区分文件是不是同一个文件
     * 说明：文件内容没变，文件名称变，这样也算是同一个文件；如果内容变化，文件名称没变，则算为不同的文件
     * 采用的加密散列算法为 SHA-256
     * @param: [fis：文件流]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/4  22:10
     **/
    public static String SHA256HashCode(InputStream fis) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");//如果想使用SHA-1或SHA-256或MD5，则传入SHA-1,SHA-256,MD5
            byte[] buffer = new byte[2048];
            int length;
            while ((length = fis.read(buffer, 0, 2048)) != -1) {
                md.update(buffer, 0, length); //循环将文件内容写入buffer缓存中，指导读取完毕
            }
            return new BigInteger(1, md.digest()).toString(16);//signnum 要转换的数-1,0,1; from源数的进制md.digest(); to要转换成的进制16进制
        } catch (NoSuchAlgorithmException e) {
            throw new RRException("当请求特定的加密算法而它在该环境中不可用时抛出此异常NoSuchAlgorithmException");
        } catch (IOException e) {
            throw new RRException("系统发生异常IOException");
        }
    }

    public static void main(String[] args) {
        try {
            //double s = div(16823753415.04150,10524.10,2);
            //System.out.println(s);
            //此处我测试的是我本机jdk源码文件的MD5值
            String md5Hashcode = SHA256HashCode("D:\\再审申请（曾明华）.doc");
            String bmd5Hashcode = SHA256HashCode("D:\\再审申请（曾明华） - 副本.doc");
//            System.out.println("a文件SHA256HashCode值：" + md5Hashcode);
//            System.out.println("b文件SHA256HashCode值：" + bmd5Hashcode);
//            System.out.println(md5Hashcode.equals(bmd5Hashcode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
