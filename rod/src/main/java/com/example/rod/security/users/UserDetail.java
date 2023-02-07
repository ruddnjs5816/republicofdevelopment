package com.example.rod.security.users;

import com.example.rod.user.entity.RoleType;
import com.example.rod.user.entity.User;
import lombok.Builder;
import org.apache.tomcat.jni.Address;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {

    private Long userId;
    private String username;
    private String name;
    private String password;
//    private Address address;
    private Long point;
    private String phonenumber;
    private RoleType role;
    private User user;

    List<GrantedAuthority> authorities;

    @Builder
    public UserDetail(Long userId, String username, String name, String password, Address address, Long point, String phonenumber, RoleType role){
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
//        this.address = address;
        this.point = point;
        this.phonenumber = phonenumber;
        this.role = role;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
