package com.paymybuddy.repository;

import com.paymybuddy.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query(value = "select connection from Connection as connection "
            +"inner join fetch connection.connectionId.user as user "
            + "where user.id = :userId "
            + "and connection.connectionId.user != connection.connectionId.connectedUser")
    List<Connection> findAllConnectionsByUserId(@Param("userId") Long userId);


    @Query(value = "select connection from Connection as connection "
            +"inner join connection.connectionId.user as user "
            +"inner join connection.connectionId.connectedUser as connectedUser "
            + "where user.id = :userId "
            + "and connectedUser.id = :connectedUserId")
    Connection findConnectionByUserIdAndConnectedUserId(@Param("userId") Long userId,@Param("connectedUserId") Long connectedUserId);
}
