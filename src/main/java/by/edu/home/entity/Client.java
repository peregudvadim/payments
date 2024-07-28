package by.edu.home.entity;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="clients")
public class Client {

    public Client() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="client_id")
    private int clientId;


    @Size(min=2, max=20, message="длина должна быть 2-20 символов")
    @Column (name="name")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Неподходящий email")
    @Column(name="email")
    private String email;

    @Pattern(regexp = "^\\+\\d{11,14}$", message = "Неподходящий номер телефона")
    @Column (name="phone")
    private String phone;


    @NotEmpty(message = "Пароль не может быть пустым")
    @Column (name="password")
    private String password;

    @Column(name= "created_at")
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @OneToOne(mappedBy = "client")
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccounts(Account account) {
        this.account = account;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


}
