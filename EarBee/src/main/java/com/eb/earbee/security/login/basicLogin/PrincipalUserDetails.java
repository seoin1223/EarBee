package com.eb.earbee.security.login.basicLogin;

import com.eb.earbee.main.entity.User;
import com.eb.earbee.security.entity.TemporaryUser;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Data
public class PrincipalUserDetails implements UserDetails, OAuth2User {

    private User user;
    private TemporaryUser temporaryUser;
    private Map<String, Object> attributes; // Oauth2

    public PrincipalUserDetails(User user) {
        this.user = user;
    }


    public PrincipalUserDetails(TemporaryUser temporaryUser, Map<String, Object> attributes) {
        this.temporaryUser = temporaryUser;
        this.attributes = attributes;
    }

    public PrincipalUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public TemporaryUser getTemporaryUser() {
        return temporaryUser != null ?  temporaryUser: null;
    }

    public boolean isTemporaryUser() {
        return temporaryUser != null;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getUsername() : (temporaryUser != null ? temporaryUser.getUsername() : "");
    }

    public String getId() {
        return user != null ? user.getId() : (temporaryUser != null ? temporaryUser.getId() : "");
    }

    @Override
    public boolean isAccountNonExpired() {
        return user != null || temporaryUser != null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user != null || temporaryUser != null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user != null || temporaryUser != null;
    }

    @Override
    public boolean isEnabled() {
        return user != null || temporaryUser != null;
    }

    @Override
    public String getName() {
        return user != null ? user.getUsername() : (temporaryUser != null ? temporaryUser.getUsername() : "");
    }


}
