package com.paymybuddy.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pay_connection")
@AssociationOverrides({@AssociationOverride(name = "connection.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "connection.connectedUser", joinColumns = @JoinColumn(name = "connected_user_id"))})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "connectionId")
public class Connection implements Serializable {


    @EmbeddedId
    private ConnectionId connectionId = new ConnectionId();


    @JsonIgnoreProperties("connection")
    @OneToMany
    @JoinColumns({ @JoinColumn(name = "user_id",
            referencedColumnName = "user_id"),
            @JoinColumn(name = "connected_user_id", referencedColumnName = "connected_user_id")
    })
    private List<Transaction> transactions;

    public ConnectionId getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(ConnectionId connectionId) {
        this.connectionId = connectionId;
    }

    public Connection connectionId(ConnectionId connectionId) {
        this.connectionId = connectionId;
        return this;
    }

    public User getUser() {
        return connectionId.getUser();
    }

    public void setUser(User user) {
        this.connectionId.setUser(user);
    }

    public Connection user(User user) {
        this.connectionId.setUser(user);
        return this;
    }
    public User getConnectedUser() {
        return connectionId.getConnectedUser();
    }

    public void setConnectedUser(User connectedUser) {
        this.connectionId.setConnectedUser(connectedUser);
    }

    public Connection connectedUser(User connectedUser) {
        this.connectionId.setConnectedUser(connectedUser);
        return this;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Connection transactions(List<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return connectionId.getUser().equals(that.getUser()) &&
                connectionId.getConnectedUser().equals(that.getConnectedUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionId.getUser(),connectionId.getConnectedUser());
    }

    @Override
    public String
    toString() {
        return "Connection{" +
                ", ConnectionId=" + connectionId +
                ", Transactions=" + transactions +
                '}';
    }
}
