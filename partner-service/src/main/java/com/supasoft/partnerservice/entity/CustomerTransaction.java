package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.BaseEntity;
import com.supasoft.partnerservice.enums.PartnerTransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Customer Transactions
 * Tracks all financial transactions with customers
 */
@Entity
@Table(name = "customer_transactions", indexes = {
        @Index(name = "idx_customer_transaction_customer", columnList = "customer_id"),
        @Index(name = "idx_customer_transaction_type", columnList = "transaction_type"),
        @Index(name = "idx_customer_transaction_date", columnList = "transaction_date"),
        @Index(name = "idx_customer_transaction_reference", columnList = "reference_number")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransaction extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 20)
    private PartnerTransactionType transactionType;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "balance_before", precision = 19, scale = 2)
    private BigDecimal balanceBefore = BigDecimal.ZERO;
    
    @Column(name = "balance_after", precision = 19, scale = 2)
    private BigDecimal balanceAfter = BigDecimal.ZERO;
    
    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    @Column(name = "reference_number", length = 50)
    private String referenceNumber;
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(length = 500)
    private String description;
}

