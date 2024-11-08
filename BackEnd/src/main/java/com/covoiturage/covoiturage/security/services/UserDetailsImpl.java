package com.covoiturage.covoiturage.security.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.covoiturage.covoiturage.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private final String id;
    private final String username;
    private final String email;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
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
