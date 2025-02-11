package com.matchango.transactions_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions") // Ensure the table name matches exactly as in the DB
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id") // Explicitly specify the column name as 'id'
  private Long id;

  @Column(name = "date") // Explicitly specify the column name for 'date'
  private LocalDateTime date;

  @Column(name = "montant", precision = 38, scale = 2) // Numeric with precision and scale
  private BigDecimal montant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false) // Foreign key column to link with Client
  @JsonBackReference
  private Client client;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public BigDecimal getMontant() {
    return montant;
  }

  public void setMontant(BigDecimal montant) {
    this.montant = montant;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
