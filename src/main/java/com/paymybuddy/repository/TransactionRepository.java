package com.paymybuddy.repository;

import com.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select transaction from Transaction as transaction "
    +"inner join fetch transaction.connection as connection "
    +"inner join fetch connection.connectionId.user as user "
    + "where user.id = :userId"
            ,countQuery = "select count(*) from Transaction as transaction " +
            "inner join transaction.connection as connection " +
            "inner join connection.connectionId.user as user " +
            "where user.id = :userId")
    Page<Transaction> findAllByUSerId(@Param("userId") Long userId, Pageable pageable);
}
