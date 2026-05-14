package com.jpmc.midascore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private Double incentive;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserRecord recipient;

    private LocalDateTime timestamp = LocalDateTime.now();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setIncentive(Double incentive) {
        this.incentive = incentive;
    }

    public Double getIncentive() {
        return incentive;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
