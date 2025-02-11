package com.matchango.transactions_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.matchango.transactions_api.Repository.*;
import com.matchango.transactions_api.model.*;

import java.math.BigDecimal;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  public Page<Transaction> getTransactions(Long clientId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size); // Page starts at 0, so page 1 is actually 0
    return transactionRepository.findByClientClientId(clientId, pageable);
  }

  public BigDecimal getTransactionSum(Long clientId) {
    return transactionRepository.sumByClientClientId(clientId);
  }
}
