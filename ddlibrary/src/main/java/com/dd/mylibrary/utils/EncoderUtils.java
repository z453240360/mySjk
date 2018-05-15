package com.dd.mylibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ***********************************************************
 * author: alex
 * time: 16/1/13 上午11:57
 * name:
 * overview:
 * usage:
 * *************************************************************
 */
public class EncoderUtils {

    public static String encoder(String str){
        try {
            //1,得到MD5的数字摘要
            MessageDigest digester = MessageDigest.getInstance("MD5");
            //2,通过数字摘要 得到 byte[]
            byte[] result = digester.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                //字节转为int类型
                int number = b & 0xff; // 加盐
                String hexStr = Integer.toHexString(number);
                if(hexStr.length()==1){
                    sb.append("0");
                }
                sb.append(hexStr);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


}
