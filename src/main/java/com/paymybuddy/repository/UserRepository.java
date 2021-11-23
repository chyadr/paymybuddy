package com.paymybuddy.repository;

import com.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    @Query(value = "select user from User as user "
            +"where user.id not in (select c.connectionId.connectedUser.id from Connection as c where c.connectionId.user.id = :id) "
            + "and user.id != :id")
    List<User> findAllNonConnectedUsersByUserId(@Param("id") Long id);



    @Query(value = "select user from User as user "
            +"left join fetch user.account  as account "
            + "where user.email = :email")
    User findUserAndAccountByEmail( @Param("email") String email);

}
