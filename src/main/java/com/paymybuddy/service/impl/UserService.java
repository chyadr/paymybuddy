package com.paymybuddy.service.impl;
import com.paymybuddy.exception.BusinessResourceException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class UserService implements IUserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = BusinessResourceException.class)
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // create account and initialize balance account with 0
        user.setAccount(new Account().balance(BigDecimal.ZERO).user(user));
        // save the user
        userRepository.save(user);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public List<User> findAllNonConnectedUsersByUserId(Long id) {
        return userRepository.findAllNonConnectedUsersByUserId(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


}

