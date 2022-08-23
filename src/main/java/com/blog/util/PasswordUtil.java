package com.blog.util;

import org.apache.shiro.crypto.hash.Sha1Hash;


/**
 * @author 31676
 */
public class PasswordUtil {

    /**
     * 对密码加密散列
     */
    public static String sha1(String source, Object salt) {
        return new Sha1Hash(source, salt,1024).toString();
    }

    public static void main(String[] args) {
        System.out.println(sha1("test","test"));
    }
}
