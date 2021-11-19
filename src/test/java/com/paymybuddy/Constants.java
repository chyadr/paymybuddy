package com.paymybuddy;

import com.paymybuddy.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

public final class Constants {

public static final Account account= new Account().id(3l).balance(BigDecimal.valueOf(1000));
    public static final User user= new User().id(1l).firstName("FirstName").lastName("LastName").email("Email").password("Password").account(account);
    public static final User connectedUser= new User().id(2l).firstName("FirstName2").lastName("LastName2").email("Email2").password("password2");
    public static final List<User> users= Collections.singletonList(connectedUser);
    public static final Connection connection=new Connection().user(user).connectedUser(connectedUser).connectionId(new ConnectionId().user(user).connectionId(connectedUser));
    public static final Transaction transaction= new Transaction().connection(connection).amount(BigDecimal.TEN).description("Description");
    public static final Transaction transactionWithoutConnection= new Transaction().amount(BigDecimal.TEN).description("Description");
    public static final List<Transaction> transactionsWithoutConnection= Collections.singletonList(transactionWithoutConnection);
    public static final List<Transaction> transactionsWithConnection= Collections.singletonList(transaction);
    public static final Page<Transaction> pageTransaction= new PageImpl<>(transactionsWithConnection);
    public static final List<Connection> connectionsWithoutConnection= Collections.singletonList(connection.transactions(transactionsWithoutConnection));
    public static final Principal principal = new Principal() {
        @Override
        public String getName() {
            return "name";
        }
    };
}

