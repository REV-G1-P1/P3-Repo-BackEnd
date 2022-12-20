package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    
    @Id
    @Column(name = "user_id", length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, length = 30, nullable = false)
    private String email;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(length = 9, nullable = false, unique = true)
    private Integer SSN;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false)
    private String password;

    @OneToOne(targetEntity = Address.class, cascade = { CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "User_Address", joinColumns = { @JoinColumn(name = "user_id")}, inverseJoinColumns = { @JoinColumn(name = "address_id") })
    private Address address;

    @OneToMany(targetEntity = AccountInformation.class, cascade = { CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "User_Account", joinColumns = { @JoinColumn(name = "user_id")}, inverseJoinColumns = { @JoinColumn(name = "account_number") })
    private List<AccountInformation> accountInformation;

    @OneToMany(targetEntity = MortgageApplication.class, cascade = { CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "User_Applications", joinColumns = { @JoinColumn(name = "user_id")}, inverseJoinColumns = { @JoinColumn(name = "application_id") })
    private List<MortgageApplication> mortgageApplication;
}