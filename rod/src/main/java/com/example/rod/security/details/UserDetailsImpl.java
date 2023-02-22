
package com.example.rod.security.details;


import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {



    private Long userId;
    private String username;
    private String password;
    private UserGrade grade;
    private User user;
    private UserRole role;

    GrantedAuthority authority;

    @Builder
    public UserDetailsImpl(Long userId, String username, String password, UserGrade grade, User user, UserRole role, GrantedAuthority authority) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.grade = grade;
        this.user = user;
        this.role = role;
        this.authority = authority;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
        authorityCollection.add(simpleGrantedAuthority);
        return authorityCollection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return username;
    }


    public User getUser() {return user;}

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
