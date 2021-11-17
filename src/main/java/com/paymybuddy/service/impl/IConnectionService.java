package com.paymybuddy.service.impl;

import com.paymybuddy.model.Connection;

import java.security.Principal;
import java.util.List;

public interface IConnectionService  {
    List<Connection> findAllConnectionsByUserId(Long userId);
    void saveConnection (Principal principal, Long connectedUserId);
}
