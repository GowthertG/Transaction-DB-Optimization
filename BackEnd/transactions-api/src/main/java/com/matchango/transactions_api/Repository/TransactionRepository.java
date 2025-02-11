package com.matchango.transactions_api.Repository;

import com.matchango.transactions_api.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  // This will return a paginated list of transactions for a client
  Page<Transaction> findByClientClientId(Long clientId, Pageable pageable);

  // Custom query to sum the "montant" for a specific client
  @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.client.clientId = :clientId")
  BigDecimal sumByClientClientId(Long clientId);
}
