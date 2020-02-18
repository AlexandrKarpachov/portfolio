package ua.carsale.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 14.02.2020
 */
public class UserDetailImpl implements UserDetails {

    private String login;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public UserDetailImpl(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.active = user.isEnabled();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getAuthority()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
        return active;
    }
}