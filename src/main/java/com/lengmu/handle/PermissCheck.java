package com.lengmu.handle;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Predicate;

@Component("pm")
public class PermissCheck {
    public boolean hasPermiss(String targetPermiss){
        System.out.println("没错就是我干的 我在检查权限");
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        System.out.println("自定义权限鉴定: "+authorities);
        GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(targetPermiss);
//        System.out.println("本次需要的权限: "+simpleGrantedAuthority+" - "+simpleGrantedAuthority.getClass());
        if (authorities.stream().filter((Predicate<GrantedAuthority>) grantedAuthority -> grantedAuthority.getAuthority().equals(targetPermiss)).count()>0){
            return true;
        }
        return false;
    }
}
