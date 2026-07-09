package com.mss301.customer.domain.entity;

import com.mss301.customer.domain.vo.CustomerRole;
import com.mss301.customer.domain.vo.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    private String cccd;

    @Column(name = "cccd_url")
    private String cccdUrl;

    @Column(name = "gplx_number")
    private String gplxNumber;

    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "gplx_url")
    private String gplxUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerRole role;

    @Column(name = "admin_note")
    private String adminNote;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
