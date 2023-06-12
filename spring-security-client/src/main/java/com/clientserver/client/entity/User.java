package com.clientserver.client.entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;


@RequiredArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
@Table
@Entity(name = "TB_ASMI_USER")
public class User implements UserDetails {

    @Id
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_NAME")
    private String username;
    @Column(name = "LAST_NAME")
    private String lastname;
    @Column(name = "PIN")
    private String password;

    @Column(name = "APP_ID")
    private String appId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = Collections.singletonList(getAppId());
        List<GrantedAuthority>  authorities = new ArrayList<>();
        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
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
