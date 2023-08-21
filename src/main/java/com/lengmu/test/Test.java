package com.lengmu.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lengmu.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/*
 * @author  lengmu
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) throws JsonProcessingException {
//     BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//     String encode = bCryptPasswordEncoder.encode("508888");
//     System.out.println(encode);
//     System.out.println("------------");
//     System.out.println(bCryptPasswordEncoder.matches("508888",encode));
        List<User> users = new ArrayList<User>(){{
            add(new User().setNickName("张三").setId(1));
            add(new User().setNickName("李四").setId(2));
            add(new User().setNickName("王五").setId(3));
            add(new User().setNickName("赵六").setId(4));
        }};
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(users);
        System.out.println(s);
        ArrayList<User> reObject = objectMapper.readValue(s, new TypeReference<ArrayList<User>>() {});
        System.out.println(reObject);
        System.out.println(reObject.get(0).getClass().toString());
    }
}
