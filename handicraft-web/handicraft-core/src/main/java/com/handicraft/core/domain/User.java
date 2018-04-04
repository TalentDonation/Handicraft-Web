package com.handicraft.core.domain;

import com.handicraft.core.dto.UserDto;
import com.handicraft.core.support.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 7299389372048448961L;

    public User() {
    }

    public User(UserDto userDto) {
        this.uid = userDto.getUid();
        this.name = userDto.getName();
        this.nickname = userDto.getNickname();
        this.phone = userDto.getPhone();
        this.address = userDto.getAddress();
        this.birthday = userDto.getBirthday();
        this.joinAt = userDto.getJoinAt();
    }

    @Id
    @Column(nullable = false)
    private long uid;
    private String name;
    private String nickname;
    private String phone;
    private String address;
    private String birthday;
    private String role;
    private String token;
    private String secretKey;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean enabled;
    private ZonedDateTime joinAt;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gid")
    private Avatar avatar;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Furniture> furnitures = new ArrayList<>();

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !accountExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return token;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void modifyAuthStatus(boolean accountExpired, boolean accountLocked, boolean enabled) {
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.enabled = enabled;
    }

    public void modifyAuthToken(String token, String secretKey) {
        this.token = token;
        this.secretKey = secretKey;
    }

}
