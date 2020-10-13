package com.project.wallet.service;

import com.project.wallet.dao.TransactionRepository;
import com.project.wallet.dao.UserRepository;
import com.project.wallet.dao.WalletRepository;
import com.project.wallet.exception.UserNotFoundException;
import com.project.wallet.exception.WalletNotFound;
import com.project.wallet.model.TransactionType;
import com.project.wallet.model.Wallet;
import com.project.wallet.model.WalletStatus;
import com.project.wallet.model.WalletUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

  private static final double INITIAL_BALANCE = 0.0;

  @Autowired
  private WalletRepository walletRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private TransactionService transactionService;

  public String createWallet() {
    String walletId = UUID.randomUUID().toString();
    Wallet newWallet = Wallet.builder().walletId(walletId).balance(INITIAL_BALANCE)
        .walletStatus(WalletStatus.ACTIVE).build();
    walletRepository.save(newWallet);
    return walletId;
  }

  public Wallet addMoneyToWallet(String username, double amount) {
    Optional<WalletUser> walletUserByUsername = userRepository.getWalletUserByUsername(username);
    if (!walletUserByUsername.isPresent()) {
      throw new UserNotFoundException("User with username " + username + "not found.");
    }
    String walletId = walletUserByUsername.get().getWalletId();
    Wallet wallet = walletRepository.findById(walletId)
        .orElseThrow(() -> new WalletNotFound("Wallet not found"));
    transactionService.createTransaction(wallet, amount, walletId, TransactionType.CREDIT);
    wallet.setBalance(wallet.getBalance() + amount);
    walletRepository.save(wallet);
    return wallet;
  }

  public Wallet transferAmountToReceiver(String username, double amount, String receiverId) {
    Optional<WalletUser> walletUserByUsername = userRepository.getWalletUserByUsername(username);
    if (!walletUserByUsername.isPresent()) {
       throw new UserNotFoundException("User with username " + username + "not found.");
    }
    String walletId = walletUserByUsername.get().getWalletId();
    Wallet wallet = walletRepository.findById(walletId)
        .orElseThrow(() -> new WalletNotFound("Wallet not found"));
    if(wallet.getBalance() < amount){
      throw new IllegalArgumentException("There is no enough balance for this transaction");
    }
    transactionService.createTransaction(wallet, amount, receiverId, TransactionType.DEBIT);
    wallet.setBalance(wallet.getBalance() - amount);
    walletRepository.save(wallet);
    return wallet;
  }

}
