package com.paymybuddy.service;

import com.paymybuddy.model.User;

import java.util.List;


public interface IUserService {

        void saveUser(User user);
        User findByEmail(String email);
        List<User> findAllNonConnectedUsersByUserId(Long id);
        User getById(Long id);
        User findUserAndAccountByEmail(String email);

}
