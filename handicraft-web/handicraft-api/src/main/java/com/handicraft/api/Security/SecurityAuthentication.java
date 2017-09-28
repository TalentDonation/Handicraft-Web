package com.handicraft.api.Security;

import com.handicraft.core.dto.User;
import com.handicraft.core.dto.UserToAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class SecurityAuthentication extends AbstractAuthenticationToken {

    private UserToAuthority userToAuthority;
    private String token;

    /*
    * 인증 전 token 삽입
    * */
    public SecurityAuthentication(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
        this.setAuthenticated(false);
    }

    /*
    * 인증 후
    * */
    public SecurityAuthentication(UserToAuthority userToAuthority) {
        super(userToAuthority.getAuthorities());
        super.setAuthenticated(userToAuthority.isEnabled());
        this.userToAuthority = userToAuthority;
        this.eraseCredentials();
    }



    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userToAuthority.getUid();
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.token = null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }


    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }

    @Override
    public UserToAuthority getDetails() {
        return userToAuthority;
    }

    @Override
    public void setDetails(Object details) {
        super.setDetails(details);
        this.userToAuthority = (UserToAuthority) details;
    }
}
