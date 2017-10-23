package com.handicraft.core.dto.Users;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.dto.Authorities.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "UserToAuthority")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToAuthority extends UserAbs implements Serializable, UserDetails {

    private static final long serialVersionUID = -5644826356153827379L;

    @OneToOne(fetch = FetchType.EAGER , cascade = {CascadeType.ALL})
    @JoinColumn(name = "aid")
    private Authority authority;


    public UserToAuthority(UserToImage userToImage)
    {
        this.uid = userToImage.getUid();
        this.address = userToImage.getAddress();
        this.birthday = userToImage.getBirthday();
        this.joinAt = userToImage.getJoinAt();
        this.name = userToImage.getName();
        this.nickname = userToImage.getNickname();
        this.phone = userToImage.getPhone();
    }

    public UserToAuthority(User user)
    {
        this.uid = user.getUid();
        this.address = user.getAddress();
        this.birthday = user.getBirthday();
        this.joinAt = user.getJoinAt();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
    }

    /*
    * UserDetails of Security
    *
    * */
    @Transient
    private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        grantedAuthorities.add(new SimpleGrantedAuthority(authority == null ? "ROLE_UNKNOWN" : "ROLE_"+authority.getRole().toString()));

        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return authority.getPassword();
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return authority.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authority.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authority.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return authority.isEnabled();
    }



}
