package com.project.wallet.service;

import com.project.wallet.dao.TransactionRepository;
import com.project.wallet.model.Transaction;
import com.project.wallet.model.Transaction.TransactionBuilder;
import com.project.wallet.model.TransactionType;
import com.project.wallet.model.Wallet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  public void createTransaction(Wallet wallet, double amount, String receiverId,
      TransactionType transactionType) {
    double openingBalance = wallet.getBalance(), closingBalance = 0.0;
    if (transactionType == TransactionType.DEBIT) {
      closingBalance = wallet.getBalance() - amount;
    } else {
      closingBalance = wallet.getBalance() + amount;
    }
    String transactionId = UUID.randomUUID().toString();
    Transaction newTransaction = Transaction.builder()
        .walletId(wallet.getWalletId())
        .amount(amount)
        .openingBalance(openingBalance)
        .closingBalance(closingBalance)
        .dateTime(LocalDateTime.now())
        .transactionId(transactionId)
        .transactionType(transactionType)
        .receiverId(receiverId)
        .build();
    transactionRepository.save(newTransaction);
  }

  public Transaction getTransactionById(String id) {
    return transactionRepository.findById(id)
        .orElseThrow(
            () -> new IllegalArgumentException("Transaction with id : " + id + " doesn't exists"));
  }

  public Transaction reverseTransaction(String transactionId) {
    Optional<Transaction> transactionToBeReversed = transactionRepository.findById(transactionId);
    if (!transactionToBeReversed.isPresent()) {
      throw new IllegalArgumentException(
          "Transaction with id : " + transactionId + " doesn't exists");
    }
    Transaction transaction = transactionToBeReversed.get();
    TransactionBuilder transactionBuilder = Transaction.builder();
    String newTransactionId = UUID.randomUUID().toString();
    if (transaction.getTransactionType() == TransactionType.CREDIT) {
      transactionBuilder.receiverId(transaction.getReceiverId())
          .walletId(transaction.getWalletId())
          .transactionType(TransactionType.DEBIT)
          .dateTime(LocalDateTime.now())
          .transactionId(newTransactionId)
          .amount(transaction.getAmount())
          .openingBalance(transaction.getOpeningBalance())
          .closingBalance(transaction.getClosingBalance() - transaction.getAmount());
    } else if (transaction.getTransactionType() == TransactionType.DEBIT) {
      transactionBuilder.receiverId(transaction.getReceiverId())
          .walletId(transaction.getWalletId())
          .transactionType(TransactionType.CREDIT)
          .dateTime(LocalDateTime.now())
          .transactionId(newTransactionId)
          .amount(transaction.getAmount())
          .openingBalance(transaction.getOpeningBalance())
          .closingBalance(transaction.getClosingBalance() + transaction.getAmount());
    }
    Transaction newTransaction = transactionBuilder.build();
    transactionRepository.save(newTransaction);
    return newTransaction;
  }

  public List<Transaction> getTransactionsFromToDate(String username, LocalDate from,
      LocalDate till) {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
        .filter(transaction ->
            transaction.getDateTime().isAfter(LocalDateTime.of(from, LocalTime.now())) &&
                transaction.getDateTime().isBefore(LocalDateTime.of(till, LocalTime.now())))
        .collect(Collectors.toList());
  }
}
