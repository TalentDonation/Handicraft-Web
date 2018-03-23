package com.handicraft.api.security;

import com.handicraft.core.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class SecurityAuthentication extends AbstractAuthenticationToken {
    private String token;
    private User user;

    public SecurityAuthentication(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
        this.setAuthenticated(false);
    }

    public SecurityAuthentication(String token, User user) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
        this.user = user;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return user.getUid();
    }

    @Override
    public void eraseCredentials() {
        this.token = null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return user.getGrantedAuthorities();
    }

    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public User getDetails() {
        return user;
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
}
