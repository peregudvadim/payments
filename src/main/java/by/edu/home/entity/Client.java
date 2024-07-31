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


    @Size(min=2, max=20, message="length should be 2-20 characters")
    @Column (name="name")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Inappropriate email")
    @Column(name="email")
    private String email;

    @Pattern(regexp = "^\\+\\d{11,14}$", message = "Inappropriate phone number")
    @Column (name="phone")
    private String phone;


    @NotEmpty(message = "Password cannot be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (clientId != client.clientId) return false;
        if (!name.equals(client.name)) return false;
        if (!email.equals(client.email)) return false;
        if (!phone.equals(client.phone)) return false;
        if (!password.equals(client.password)) return false;
        if (!createdAt.equals(client.createdAt)) return false;
        return account.equals(client.account);
    }

    @Override
    public int hashCode() {
        int result = clientId;
        result = 31 * result + name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}
