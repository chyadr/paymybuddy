package com.paymybuddy;

import com.paymybuddy.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ConstantsTest {

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
    public static final Principal principal = () -> "name";
    public static final UserDetails userDetails= new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return "email";
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    };

}

