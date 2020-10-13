package com.project.wallet.apis;

import com.project.wallet.model.Wallet;
import com.project.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;

  @PutMapping("/recharge/{username}/{amount}")
  public ResponseEntity<?> addMoneyToWallet(@PathVariable("username") String username,
                                            @PathVariable("amount") double amount){
    Wallet wallet = walletService.addMoneyToWallet(username, amount);
    return ResponseEntity.ok(wallet);
  }

  @PostMapping("transfer/{from}/{to}/{amount}")
  public ResponseEntity<?> transferMoney(@PathVariable("from") String fromUserId,
                                         @PathVariable("to") String toUserId,
                                         @PathVariable("amount") double amount){
    Wallet wallet = walletService.transferAmountToReceiver(fromUserId, amount, toUserId);
    return ResponseEntity.ok(wallet);
  }



}
