package com.example.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "db1_virtual_contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Db1VirtualContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relationship_type", length = 255)
    private String relationshipType;

    @Column(name = "private_contact", length = 255)
    private String privateContact;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "middle_name", length = 255)
    private String middleName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "nick_name", length = 255)
    private String nickName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Db1UserContact> userContacts = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

