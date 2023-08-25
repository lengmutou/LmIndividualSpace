package com.lengmu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lengmu.entity.MyUserDetails;
import com.lengmu.util.JwtUtil;
import com.lengmu.util.RedisKeyPrefix;
import com.lengmu.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
 * @author  lengmu
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
//@Api(description = "用户操作",tags = "user-controller")
@Tag(name="user-controller",description = "用户操作")
public class UserController {

    @Resource
    AuthenticationManager authenticationManager;
    @Resource
    RedisTemplate<String,String> redisTemplate;

    @PostMapping("/verification")
    @ApiOperation("获取验证码")
    public void getVerification(HttpSession session, HttpServletResponse response,@ApiParam(name = "currentSessionID",value = "当前会话ID") @RequestBody Map params) {
        //获取请求对象传递的不会重复的用来代表他的串
        String currentSessionID = params.get("currentSessionID").toString();

        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        // 图形验证码写出，可以写出到文件，也可以写出到流
        String code = lineCaptcha.getCode();
        // 代表对象的串当key  给他生成的有效的验证码为value  后面用来对比   并设定过期时间
        System.out.println("生成验证码： " + code);
        redisTemplate.opsForValue().set(currentSessionID,code,5, TimeUnit.MINUTES);

        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "No-cache");
        try {
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> params) throws JsonProcessingException {
        System.out.println("正在登陆~~~~");
        System.out.println("验证码: "+redisTemplate.opsForValue().get(params.get("currentSessionID")));
        //验证验证码
        if (params.get("verification").equalsIgnoreCase(redisTemplate.opsForValue().get(params.get("currentSessionID")))){
            System.out.println("验证码正确");
            //验证成功 删除redis存储的验证码
            redisTemplate.delete(params.get("currentSessionID"));
            //认证账号是否存在
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(params.get("account"), params.get("password")));
            //如果为空就是不存在 直接抛出异常 会有异常处理器
            if (authenticate==null){
                return Result.failed("账号或密码错误,请修改后重新登陆");
            }
            //登录成功 存储用户信息到redis 生成并返回token给前端
            MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
            //将用户信息 存入redis
            String userInfoKey = RedisKeyPrefix.CACHE_USER_KEY+myUserDetails.getUser().getId();
            redisTemplate.opsForValue().set(userInfoKey,new ObjectMapper().writeValueAsString(myUserDetails),RedisKeyPrefix.CACHE_USER_KEY_TTL,TimeUnit.HOURS);
            //通过key生成token
            String token = JwtUtil.createJWT(userInfoKey, RedisKeyPrefix.CACHE_USER_KEY_MS);

            //然后再将存入redis的key 生成token 返回给前端 前端后面访问的时候在请求头中携带这个token 在redis中查询 来验证是否登陆
            System.out.println("登陆成功！！");
            //验证通过
            return Result.success(token,"登陆成功！稍后跳转");
        }else{
            return Result.failed("验证码错误，请重新输入！");
        }
    }
}
