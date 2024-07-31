package by.edu.home.entity;


import by.edu.home.constant.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name="credit_cards")

public class CreditCard {

    public CreditCard() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private int cardId;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name="cvv")
    private String cvv;

    @Column(name= "created_at")
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "balance")
    private BigDecimal balance;


    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        LocalDate localExpirationDate = LocalDate.now().plusYears(1);
        expirationDate = Date.from(localExpirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int creditCardId) {
        this.cardId = creditCardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCard that = (CreditCard) o;

        if (cardId != that.cardId) return false;
        if (!cardNumber.equals(that.cardNumber)) return false;
        if (!expirationDate.equals(that.expirationDate)) return false;
        if (!cvv.equals(that.cvv)) return false;
        if (!createdAt.equals(that.createdAt)) return false;
        if (status != that.status) return false;
        if (!balance.equals(that.balance)) return false;
        return account.equals(that.account);
    }

    @Override
    public int hashCode() {
        int result = cardId;
        result = 31 * result + cardNumber.hashCode();
        result = 31 * result + expirationDate.hashCode();
        result = 31 * result + cvv.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}
