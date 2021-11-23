package com.paymybuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {


    @Id
    @GeneratedValue(generator = "seq_ids")
    @SequenceGenerator(name = "seq_ids", sequenceName = "seq_ids", allocationSize = 1)
    private Long id;
    @JsonIgnoreProperties("user")
    @OneToOne
    private User user;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccount id(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccount user(User user) {
        this.user = user;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public BankAccount iban(String iban){
        this.iban=iban;
        return this;
    }
    public BankAccount bic(String bic){
        this.bic=bic;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount account = (BankAccount) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", user=" + user +
                ", iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                '}';
    }
}
