package by.edu.home.entity;


import jakarta.persistence.*;
import by.edu.home.constant.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "accounts")

public class Account {

    public Account() {
        this.accountNumber = generateAccountNumber();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(10,2) default '0.00'")
    private BigDecimal balance=BigDecimal.valueOf(0.00);


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Column(name = "account_number", unique = true)
    private String accountNumber;


    @OneToMany(mappedBy = "account")
    private List<Payment> payments;

    @OneToMany(mappedBy = "account")
    private List<CreditCard> creditCards;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Client getClient() {
        return client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != account.accountId) return false;
        if (!balance.equals(account.balance)) return false;
        if (status != account.status) return false;
        if (!createdAt.equals(account.createdAt)) return false;
        if (!accountNumber.equals(account.accountNumber)) return false;
        if (!payments.equals(account.payments)) return false;
        if (!creditCards.equals(account.creditCards)) return false;
        return client.equals(account.client);
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + balance.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + accountNumber.hashCode();
        result = 31 * result + payments.hashCode();
        result = 31 * result + creditCards.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }
}
