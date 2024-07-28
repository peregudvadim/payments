package by.edu.home.transfer;

import java.math.BigDecimal;
import java.util.List;

public class AccountDTO {
    private int accountId;
    private String status;
    private String name;
    private String email;
    private String phone;
    private BigDecimal balance;
    private List<CreditCardDTO> creditCards;

    public AccountDTO() {
    }

    public AccountDTO(int accountId, String status, String name, String email, String phone, BigDecimal balance, List<CreditCardDTO> creditCards) {
        this.accountId = accountId;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.creditCards = creditCards;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<CreditCardDTO> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCardDTO> creditCards) {
        this.creditCards = creditCards;
    }
}