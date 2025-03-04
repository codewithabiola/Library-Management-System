package com.example.UserLibrary.users;


import com.example.UserLibrary.borrowrecord.BorrowRecord;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String email;
    private UserRole userRole;

    @OneToMany
    private List<BorrowRecord> borrowRecords;

    public Users() {
    }

    public Users(int id, String name, String email, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
