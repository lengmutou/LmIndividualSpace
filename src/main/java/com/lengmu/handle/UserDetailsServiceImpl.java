package com.lengmu.handle;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lengmu.dao.UserDao;
import com.lengmu.entity.MyUserDetails;
import com.lengmu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *  根据用户名获取用户信息 然后与客户端传送的信息对比
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("我是本次登录的用户名: "+username);
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount,username);
        User user = userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        //根据用户查询权限信息 添加到LoginUser中
        List<String> permiss = userMapper.queryUserPermiss(user.getId());
        List<String> roles = userMapper.queryUserRoles(user.getId());
        //封装成UserDetails对象返回
        return new MyUserDetails(user,permiss,roles);
    }

}

