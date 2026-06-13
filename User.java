package com.mabentech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String password;

    private String phone;

    private String country;

    private String currency; // USD, EUR, GBP, NGN, etc.

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean enabled;

    private Boolean emailVerified;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (enabled == null) enabled = true;
        if (emailVerified == null) emailVerified = false;
        if (role == null) role = Role.CUSTOMER;
        if (currency == null) currency = "USD";
    }

    public enum Role {
        CUSTOMER, ADMIN, MODERATOR
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
