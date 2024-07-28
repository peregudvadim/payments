package by.edu.home.transfer;

import java.math.BigDecimal;
import java.util.Date;

public class CreditCardDTO {
    private int cardId;
    private String cardNumber;
    private Date expirationDate;
    private BigDecimal balance;
    private String status;
    public CreditCardDTO() {
    }

    public CreditCardDTO(int cardId, String cardNumber, Date expirationDate, BigDecimal balance, String status) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.status = status;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
