package by.edu.home.entity;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "administrators")
public class Administrator {

    public Administrator() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="admin_id")
    private int adminId;

    @Column(name = "name")
    private String name;

    @Column(name="email")
    private String email;

    @Column (name="phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public int getAdminId() {
        return adminId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
