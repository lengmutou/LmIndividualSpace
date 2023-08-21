package com.lengmu.handle;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * @author  lengmu
 * @version 1.0
 */
public class Md5PasswordEncoder implements PasswordEncoder {

    //注册时使用
    @Override
    public String encode(CharSequence rawPassword) {
//        MessageDigestPasswordEncoder messageDigestPasswordEncoder = new MessageDigestPasswordEncoder("MD5");
//        messageDigestPasswordEncoder.setIterations(iterations);
//        return messageDigestPasswordEncoder.encode(rawPassword.toString() + salt);
        System.out.println("原始密码"+rawPassword.toString());
        System.out.println(rawPassword);
        return new BCryptPasswordEncoder().encode(rawPassword);

    }
    //登陆时使用
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("-----------");
        System.out.println(rawPassword);
        System.out.println(encodedPassword);
        System.out.println("-----------");
        return new BCryptPasswordEncoder().matches(rawPassword,encodedPassword);
    }
}
