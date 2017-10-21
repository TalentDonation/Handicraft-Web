package com.handicraft.core.dto.Authorities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity(name = "Authority")
@Table(name = "authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Authority implements Serializable{


    private static final long serialVersionUID = 3707911395954475461L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long aid;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String password;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialsExpired;

    private boolean credentialsLocked;

    private boolean enabled;

}
