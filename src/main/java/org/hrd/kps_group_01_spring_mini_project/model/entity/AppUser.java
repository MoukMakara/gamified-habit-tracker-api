package org.hrd.kps_group_01_spring_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {
    private UUID appUserId;
    private String username;
    private String email;
    private String password;
    private Integer level;
    private Integer xp;
    private String profileImageUrl;
    private Boolean isVerified;
    private LocalDateTime createdAt;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(email));
        return null;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
