package cn.lucas.ad.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtils {


    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
