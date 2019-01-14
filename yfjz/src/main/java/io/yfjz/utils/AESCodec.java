package io.yfjz.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * class_name: AESCodec
 *
 * @Description:
 * @author: 饶士培
 * @QQ: 1013147559@qq.com
 * @tel: 18798010686
 * @date: 2018-08-01 9:24
 */
public class AESCodec {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";
    //private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128;
    private static final String CHARSET = "UTF-8";
    private static final String KEY_PATH = "d:\\AES.txt";
    private static byte[] key = null;
    private static byte[] key_new = null;
    private static String keyStr = null;

    public static byte[] decrypt(byte[] data, byte[] key)
            throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        //SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        //Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(2, skeySpec);
        return trim(cipher.doFinal(data));
    }

    public static byte[] decrypt(byte[] data, String key)
            throws Exception
    {
        return decrypt(data, HexCodec.hexDecode(key));
    }

    public static String decrypt(String data, String key)
            throws Exception
    {
        return new String(decrypt(HexCodec.hexDecode(data), key), "UTF-8");
    }

    public static byte[] encrypt(byte[] data, byte[] key)
            throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        //Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(1, skeySpec);
        return cipher.doFinal(padding(data));
    }

    public static byte[] encrypt(byte[] data, String key)
            throws Exception
    {
        return encrypt(data, HexCodec.hexDecode(key));
    }

    public static String encrypt(String data, String key)
            throws Exception
    {
        return HexCodec.hexEncode(encrypt(data.getBytes("UTF-8"), key));
    }

    private static byte[] padding(byte[] data)
    {
        return padding(data, 16);
    }

    private static byte[] padding(byte[] data, int len)
    {
        int length = data.length;
        int remainder = length % len;
        if (remainder == 0) {
            return data;
        }
        byte[] newData = new byte[length + (len - remainder)];
        System.arraycopy(data, 0, newData, 0, length);
        return newData;
    }

    private static byte[] trim(byte[] data)
    {
        int length = data.length;

        int counter = 0;
        for (int i = 1; i < 17; i++) {
            if (data[(length - i)] == 0) {
                counter++;
            }
        }
        return Arrays.copyOfRange(data, 0, length - counter);
    }

    public static byte[] genKey()
            throws Exception
    {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        SecretKey secretKey = kg.generateKey();
        byte[] key = secretKey.getEncoded();

        return key;
    }

    private static byte[] encryptMD5(String data)
    {
        byte[] bytes = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
        return bytes;
    }

    public static void main(String[] args)
    {
        try
        {
            String localPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"AES.txt";

            String src = getAESDecode_new("����", localPath);
            System.out.println("====>" + src);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getAESEncode(String data, String keyFilePath)
            throws Exception
    {
        if (key == null)
        {
            File file = new File(keyFilePath);
            key = new byte[(int)file.length()];
        }
        return HexCodec.hexEncode(encrypt(data.getBytes("UTF-8"), key));
    }

    public static String getAESDecode(String data, String keyFilePath)
            throws Exception
    {
        if (key == null)
        {
            File file = new File(keyFilePath);
            key = new byte[(int)file.length()];
        }
        return new String(decrypt(HexCodec.hexDecode(data), key), "UTF-8");
    }

    private static Cipher cipherEncrypt = null;
    private static Cipher cipherDecrypt = null;
    private static SecretKeySpec keyspec = null;
    private static IvParameterSpec ivspec = null;

    public static String encryptAES(String data, String key, String iv)
            throws Exception
    {
        try
        {
            if (cipherEncrypt == null)
            {
                cipherEncrypt = Cipher.getInstance("AES/CBC/NoPadding");
                keyspec = new SecretKeySpec(key.getBytes(), "AES");
                ivspec = new IvParameterSpec(iv.getBytes());
                cipherEncrypt.init(1, keyspec, ivspec);
            }
            int blockSize = cipherEncrypt.getBlockSize();
            byte[] dataBytes = data.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            byte[] encrypted = cipherEncrypt.doFinal(plaintext);

            return new BASE64Encoder().encode(encrypted).trim();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptAES(String data, String key, String iv) throws Exception {

        String originalString = null;
        try {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            if (cipherDecrypt == null)
            {
                cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
                //cipherDecrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
                keyspec = new SecretKeySpec(key.getBytes(), "AES");
                ivspec = new IvParameterSpec(iv.getBytes());
                cipherDecrypt.init(2, keyspec, ivspec);
            }
            byte[] original = cipherDecrypt.doFinal(encrypted1);
            originalString = new String(original, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return originalString==null?null:originalString.trim();

    }

    public static String getAESEncode_new(String data, String keyFilePath)
            throws Exception
    {
        if (keyStr == null)
        {
            File file = new File(keyFilePath);
            key_new = new byte[(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(key_new);
            fis.close();
            keyStr = HexCodec.hexEncode(key_new);
        }
        return encryptAES(data, keyStr.substring(0, 16), keyStr.substring(16, 32));
    }

    public static String getAESDecode_new(String data, String keyFilePath)
            throws Exception
    {
        if (keyStr == null)
        {
            File file = new File(keyFilePath);
            key_new = new byte[(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(key_new);
            fis.close();
            keyStr = HexCodec.hexEncode(key_new);
        }
        String decode = null;
        try{
             decode = decryptAES(data, keyStr.substring(0, 16), keyStr.substring(16, 32));
        }catch (Exception e){
            e.printStackTrace();
        }

        return (decode != null) && (!"".equals(decode)) ? decode : data;
    }
}
