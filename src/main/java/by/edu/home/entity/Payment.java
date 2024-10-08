package by.edu.home.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name="payments")
public class Payment {

    public Payment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private int paymentId;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name= "payment_date")
    private Timestamp paymentDate;

    @PrePersist
    protected void onCreate() {
        paymentDate = new Timestamp(System.currentTimeMillis());
    }
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paymentId != payment.paymentId) return false;
        if (!amount.equals(payment.amount)) return false;
        if (!paymentDate.equals(payment.paymentDate)) return false;
        return account.equals(payment.account);
    }

    @Override
    public int hashCode() {
        int result = paymentId;
        result = 31 * result + amount.hashCode();
        result = 31 * result + paymentDate.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}

