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
@Table(name = "mortgage_Applications")
public class MortgageApplication {
    
    @Id
    @Column(name = "application_id", length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(length = 9, nullable = false)
    private Integer ssn;

    @Column(nullable = false)
    private Integer income;

    @Column(name = "loan_address", nullable = false)
    private String loanAddress;

    @Column(name = "home_value", length = 15, nullable = false)
    private Double homeValue;

    @Column(name = "loan_amount", length = 15, nullable = false)
    private Double loanAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

}
