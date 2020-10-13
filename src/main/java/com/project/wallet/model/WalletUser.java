package com.project.wallet.model;

import javax.persistence.Entity;
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
public class WalletUser {
   @Id
   private String userId;
   private String username;
   private String password;
   private String email;
   private String walletId;
}
