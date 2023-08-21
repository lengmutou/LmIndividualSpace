package com.lengmu.config;

import com.lengmu.filter.TokenStatusCheckFilter;
import com.lengmu.handle.AccessDeniedHandler;
import com.lengmu.handle.AuthenticationFailedHandle;
import com.lengmu.handle.LoginSuccessHandle;
import com.lengmu.handle.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @Author qt
 * @Date 2021/3/25
 * @Description SpringSecurity安全框架配置
 */
@Configuration
@EnableWebSecurity//开启Spring Security的功能
////prePostEnabled属性决定Spring Security在接口前注解是否可用@PreAuthorize,@PostAuthorize等注解,设置为true,会拦截加了这些注解的接口
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationFailedHandle authenticationFailedHandle;

    @Autowired
    TokenStatusCheckFilter tokenStatusCheckFilter;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;
    @Autowired
    LoginSuccessHandle loginSuccessHandle;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
//                不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //让静态资源通行
                .antMatchers("/js/**","/css/**","/fonts/**","/images/**","/js/**","/layui/**","/lib/**").anonymous()
                //让跳转页面的请求通过
                .antMatchers("/**/jump/**").anonymous()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
//                .antMatchers("/article/").anonymous()
                .antMatchers("/user/verification").anonymous()
                .antMatchers("/login").anonymous()
                .antMatchers("/").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                //指定登录页  所有权限都能访问
                .and().formLogin().loginPage("http://localhost:8081/").permitAll().successHandler(loginSuccessHandle)
                //添加验证登录状态的过滤器
                .and().addFilterBefore(tokenStatusCheckFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();
        //设置身份认证失败处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationFailedHandle).accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new Md5PasswordEncoder();
    }
}
