package com.matchango.transactions_api.Controller;

import com.matchango.transactions_api.model.*;
import com.matchango.transactions_api.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  // Endpoint to get paginated transactions for a specific client
  @GetMapping("/list/{clientId}")
  public ResponseEntity<Page<Transaction>> getTransactionsByClientId(
      @RequestParam(defaultValue = "0") int page,
      @PathVariable Long clientId,
      @RequestParam(defaultValue = "10") int size) {

    Page<Transaction> transactions = transactionService.getTransactions(clientId, page, size);

    if (transactions.isEmpty()) {
      return ResponseEntity.noContent().build(); // Return 204 if no transactions are found
    }

    return ResponseEntity.ok(transactions); // Return 200 with the transactions
  }

  // Endpoint to get the sum of transactions for a specific client
  @GetMapping("/sum/{clientId}")
  public ResponseEntity<BigDecimal> getTransactionSum(@PathVariable Long clientId) {
    BigDecimal sum = transactionService.getTransactionSum(clientId);

    if (sum == null || sum.compareTo(BigDecimal.ZERO) == 0) {
      return ResponseEntity.noContent().build(); // Return 204 if sum is 0 or null
    }

    return ResponseEntity.ok(sum); // Return 200 with the sum
  }
}
