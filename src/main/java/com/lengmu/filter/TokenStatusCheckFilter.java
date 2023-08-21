package com.lengmu.filter;

import com.alibaba.fastjson2.JSONObject;
import com.lengmu.entity.MyUserDetails;
import com.lengmu.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.lengmu.util.RedisKeyPrefix.CACHE_USER_KEY;

@Component
//OncePerRequestFilter 默认实现filter的方法有bug？ 有可能会重复拦截多次 这个是一个请求只会拦截一次
public class TokenStatusCheckFilter extends OncePerRequestFilter {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取请求方法
        String method = request.getMethod();
        // 如果是 OPTIONS 请求，直接放行并设置 CORS 头信息
        if ("OPTIONS".equalsIgnoreCase(method)) {
//            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
//            response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type,Token");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Access-Control-Allow-Origin","*");
            return;
        }


        //获取token
        String token = request.getHeader("token");
        System.out.println("我是token: "+token);
        //用户不一定携带token 所以 有可能为空
        if (!StringUtils.hasText(token)) {
            System.out.println("我已经放行了 不是我干的");
            //如果token为空的话 说明他就不是已登录状态 不进行操作 直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //到这里肯定是有token的 但是token有可能被更改什么的 所以有可能解析token出错
        String userKey;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            //解析出之前存入的内容 也就是用户id
            userKey = claims.getSubject();
            System.out.println("我是token解析的结果: "+userKey);
        } catch (Exception e) {
            throw new RuntimeException("token状态异常");
        }
        //通过用户id在redis中寻找用户信息 如果找到了 给用户认证  否则 放行交由后续处理
        String userDetailsString = (String)redisTemplate.opsForValue().get(userKey);
        MyUserDetails userDetails = (MyUserDetails) JSONObject.parseObject(userDetailsString, MyUserDetails.class);
        //如果未在redis中找到
        if (Objects.isNull(userDetails)) {
            //那就是用户未登录/已经注销了 但是还是携带了之前的token
            throw new RuntimeException("用户未登录!");
        }
//        loginUser.getPermissionsListStr().forEach(System.out::println);
//        System.out.println("我是用户: "+loginUser.getUser().getUsername());
//        System.out.println("权限字符串: "+loginUser.getPermissionsListStr());
//        System.out.println("权限: "+loginUser.getAuthorities());

        //如果redis中找到了 认证状态
        //3个参数的构造方法用来生成被认证的用户令牌
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUser(), null, userDetails.getAuthorities());
        //放入这个里面 让后面也能够访问到认证状态
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        System.out.println("token验证成功");
        System.out.println(userDetails);
        filterChain.doFilter(request, response);
    }
}
