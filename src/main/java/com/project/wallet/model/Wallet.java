package com.project.wallet.model;

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
public class Wallet {
   @Id
   private String walletId;
   @Enumerated(EnumType.STRING)
   private WalletStatus walletStatus;
   private double balance;
}
