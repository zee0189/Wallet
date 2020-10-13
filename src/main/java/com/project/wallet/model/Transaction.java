package com.project.wallet.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
   @Id
   private String transactionId;
   private String walletId;
   private String receiverId;
   private double openingBalance;
   private double closingBalance;
   private LocalDateTime dateTime;
   private double amount;
   @Enumerated(EnumType.STRING)
   private TransactionType transactionType;
}
