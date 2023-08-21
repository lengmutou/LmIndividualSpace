package com.lengmu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author lengmu
 */
@Data
@NoArgsConstructor

public class MyUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;
    private List<String> permissionsListStr;
    private List<String> roles;

    public MyUserDetails(User user, List<String> permissionsListStr,List<String> roles) {
        this.user = user;
        this.permissionsListStr = permissionsListStr;
        this.roles=roles;
    }
    public List<String> getRoles(){
        return roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissionsListStr!=null){
            return permissionsListStr.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return new ArrayList<GrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

