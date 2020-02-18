package ua.carsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.carsale.domain.User;
import ua.carsale.domain.UserDetailImpl;
import ua.carsale.persistance.UserRepository;

import java.util.ArrayList;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 14.02.2020
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
        return new UserDetailImpl(user);
    }
}
