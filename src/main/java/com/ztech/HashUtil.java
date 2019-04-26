package com.ztech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    private static Logger LOG = LoggerFactory.getLogger(HashUtil.class);

    /**
     * 计算字符串的hash值
     * @param string    明文
     * @param algorithm 算法名
     * @return          字符串的hash值
     */
    public static String hash(String string, String algorithm) {
        if (string.isEmpty()) {
            return "";
        }
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance(algorithm);
            byte[] bytes = hash.digest(string.getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static void main(String args[]) {
        String name ="张三";
        String id = "1101071990101011234";
        String idtype = "I";
        String gender = "F";
        String birth = "1990-01-01";

        String str = name + "," + id + "," + idtype + "," + gender + "," + birth;
        System.out.println("str: "+str);

        String hashFileMD5 = HashUtil.hash(str, "MD5");
        System.out.println("hash算法文件的MD5值为：" + hashFileMD5);
        String hashFileSHA1 = HashUtil.hash(str, "SHA1");
        System.out.println("hash算法文件的SHA1值为：" + hashFileSHA1);

    }





}
