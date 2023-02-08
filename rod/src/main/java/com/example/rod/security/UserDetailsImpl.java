
/*
package com.example.rod.security.users;
=======
package com.example.rod.security;
>>>>>>> b2475491e68d3998f612a71c061c94b7072113c4:rod/src/main/java/com/example/rod/security/UserDetailsImpl.java

import com.example.rod.user.entity.RoleType;
import com.example.rod.user.entity.User;
import lombok.Builder;
import org.apache.tomcat.jni.Address;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final Long userId;
    private final String username;
    private RoleType role;
    private User user;

    List<GrantedAuthority> authorities;

    @Builder
    public UserDetailsImpl(Long userId, String username, RoleType role){
        this.userId = userId;
        this.username = username;
        this.role = role;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       RoleType role = user.getRole();
       String Authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(Authority);
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
*/
