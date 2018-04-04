package com.handicraft.api.security;

import com.handicraft.core.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class SecurityAuthentication extends AbstractAuthenticationToken {
    private User user;
    private String token;

    public SecurityAuthentication(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
        this.setAuthenticated(false);
    }

    public SecurityAuthentication(String token, User user) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.user = user;
        this.token = token;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.user.getUid();
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) user.getAuthorities();
    }

    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public User getDetails() {
        return this.user;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }

    @Override
    public void setDetails(Object details) {
        super.setDetails(details);
        this.user = (User) details;
    }

    @Override
    public String toString() {
        return "SecurityAuthentication{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
