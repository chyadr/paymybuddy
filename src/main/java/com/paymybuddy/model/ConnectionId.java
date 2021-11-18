package com.paymybuddy.model;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Embeddable
public class ConnectionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "connected_user_id",referencedColumnName = "id")
    private User connectedUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConnectionId user(User user) {
        this.user=user  ;
        return this;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    public ConnectionId connectionId(User connectedUser) {
        this.connectedUser=connectedUser  ;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionId that = (ConnectionId) o;
        return Objects.equals(user, that.user) && Objects.equals(connectedUser, that.connectedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, connectedUser);
    }

    @Override
    public String toString() {
        return "ConnectionId{" +
                "userId=" + user +
                ", connectedUserId=" + connectedUser +
                '}';
    }
}

