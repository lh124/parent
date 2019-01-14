package io.yfjz.utils.encrypt;

import io.yfjz.utils.RRException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @class_name: Encrypt
 * @describe: 加密算法工具
 * @author: 刘琪
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018/1/4  15:10
 **/
public class EncryptUtils {

    /**
     * @method_name: SHA256
     * @describe: 传入文本内容，返回 SHA-256 串
     * @param: [strText]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/4  16:13
     **/
    public String SHA256(String strText) {
        return SHA(strText, "SHA-256");
    }

    /**
     * @method_name: SHA512
     * @describe: 传入文本内容，返回 SHA-512 串
     * @param: [strText]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/4  16:13
     **/
    public String SHA512(String strText) {
        return SHA(strText, "SHA-512");
    }

    /**
     * @method_name: SHA
     * @describe: 字符串 SHA 加密
     * @param: [strText：加密文本, encryptType：加密类型]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/4  16:12
     **/
    private String SHA(String strText, String encryptType) {
        if (strText == null || strText.length() == 0)
            throw new RRException("请求加密的字符串为空,请检查!");
        try {
            // SHA 加密开始 创建加密对象 并传入加密类型
            MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
            // 传入要加密的字符串
            messageDigest.update(strText.getBytes("UTF-8"));
            // 得到 byte 类型结果
            byte[] bytes = messageDigest.digest();
            return toHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RRException("当请求特定的加密算法而它在该环境中不可用时抛出此异常NoSuchAlgorithmException");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RRException("当请求特定的加密算法,不支持 UTF-8 这种编码方式 UnsupportedEncodingException");
        }
    }

    /**
     * @method_name: saltGenerate
     * @describe: salt盐生成器
     * 说明：0xff & salt[i]
     * salt[i]是随机的一个byte 8位，0xff 是int类型 32位，高24位全部是0，低8位全部是1
     * & 运算  0&0 = 0; 1&0 = 0 0&1 = 0; 1&1 = 1
     * 这个& 运算确保计算出来的数据是正整数
     * @param: []
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/4  16:31
     **/
    private String saltGenerate() {
        Random ranGen = new SecureRandom();
        byte[] salt = new byte[20];
        ranGen.nextBytes(salt);
        return toHexString(salt);
    }


    /**
     * @method_name: toHexString
     * @describe: 将bytes数组转为十进制字符串，多用于加密算法之中
     * @param: [bytes：需要转换的byte数组]
     * @return java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2018/1/4  17:53
     **/
    private String toHexString (byte[] bytes){
        StringBuffer strHexString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1)
                strHexString.append("0");
            strHexString.append(hex);
        }
        return strHexString.toString();
    }

    public static void main(String[] args) {
        EncryptUtils encrypt = new EncryptUtils();
//        System.out.println(encrypt.saltGenerate());

       /* String strText = "";
        if (strText == null || strText.length() == 0)
            throw new RRException("请求加密的字符串为空,请检查!");*/

        String ss = encrypt.SHA512("123456");
//        System.out.println(ss);
        String bb = encrypt.saltGenerate();
//        System.out.println(bb);



/*

        long a = 5<<3;
        System.out.println(a);

        BigDecimal d = new BigDecimal(Math.pow(2,3));
        System.out.println(d.intValue());
        System.out.println(d.longValue());
*/
    }


}
