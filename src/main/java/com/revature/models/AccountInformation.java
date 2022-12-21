package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_information")
public class AccountInformation {
    
    @Id
    @Column(name = "account_number", length = 8)
    private Integer accountNumber;

    @Column(name = "routing_number", length = 9, nullable = false)
    private Integer routingNumber;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "account_name", length = 40)
    private String accountName;

}
