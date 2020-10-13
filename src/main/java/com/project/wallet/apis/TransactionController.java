package com.project.wallet.apis;

import com.project.wallet.model.Transaction;
import com.project.wallet.service.TransactionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @GetMapping("/detail/{id}")
  public ResponseEntity<?> getTransactionById(@PathVariable("id") String transactionId) {
    Transaction transactionById = transactionService.getTransactionById(transactionId);
    return ResponseEntity.ok(transactionById);
  }

  @PutMapping("/reverse/{id}")
  public ResponseEntity<?> transactionReversal(@PathVariable("id") String transactionId) {
    Transaction transaction = transactionService.reverseTransaction(transactionId);
    return ResponseEntity.ok(transaction);
  }

  @GetMapping("/passbook/{fromDate}/{tillDate}")
  public ResponseEntity<?> getTransactionsFromGivenDateToTillDate(
      @PathVariable("username") String username,
      @PathVariable("from") String from,
      @PathVariable("till") String till) {
    List<Transaction> transactionsFromToDate = transactionService
        .getTransactionsFromToDate(username, LocalDate.parse(from), LocalDate.parse(till));
    return ResponseEntity.ok(transactionsFromToDate);
  }
}
