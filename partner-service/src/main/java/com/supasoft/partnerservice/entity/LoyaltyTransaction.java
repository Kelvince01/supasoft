package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity for Loyalty Transactions
 * Tracks earning and redemption of loyalty points
 */
@Entity
@Table(name = "loyalty_transactions", indexes = {
        @Index(name = "idx_loyalty_customer", columnList = "customer_id"),
        @Index(name = "idx_loyalty_date", columnList = "transaction_date"),
        @Index(name = "idx_loyalty_expiry", columnList = "expiry_date"),
        @Index(name = "idx_loyalty_reference", columnList = "reference_number")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyTransaction extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Size(max = 50, message = "Transaction type must not exceed 50 characters")
    @Column(name = "transaction_type", nullable = false, length = 50)
    private String transactionType; // EARN, REDEEM, EXPIRE, ADJUST
    
    @Column(name = "points_earned")
    private Integer pointsEarned = 0;
    
    @Column(name = "points_redeemed")
    private Integer pointsRedeemed = 0;
    
    @Column(name = "points_balance")
    private Integer pointsBalance = 0;
    
    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    @Column(name = "reference_number", length = 50)
    private String referenceNumber;
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(length = 500)
    private String description;
}

