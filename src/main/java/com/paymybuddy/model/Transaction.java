package com.paymybuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import liquibase.pro.packaged.L;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "pay_transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_ids")
    @SequenceGenerator(name = "seq_ids", sequenceName = "seq_ids", allocationSize = 1)
    private Long id;

    @JsonIgnoreProperties("transactions")
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "user_id",
            referencedColumnName = "user_id"),
            @JoinColumn(name = "connected_user_id", referencedColumnName = "connected_user_id")
    })
    private Connection connection;

    @Column(name = "amount")
    private BigDecimal amount ;

    @Column(name = "description")
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction id(Long id) {
        this.id=id  ;
        return this;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Transaction connection (Connection connection) {
        this.connection= connection  ;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transaction amount (BigDecimal amount) {
        this.amount=amount  ;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction description(String description) {
        this.description=description  ;
        return this;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "transaction{" +
                "id=" + id +
                ", Connection=" + connection +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}

