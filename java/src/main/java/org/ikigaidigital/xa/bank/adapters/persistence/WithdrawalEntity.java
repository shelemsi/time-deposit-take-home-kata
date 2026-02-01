package org.ikigaidigital.xa.bank.adapters.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "withdrawals")
public class WithdrawalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "time_deposit_id", nullable = false)
    private TimeDepositEntity timeDeposit;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TimeDepositEntity getTimeDeposit() { return timeDeposit; }
    public void setTimeDeposit(TimeDepositEntity timeDeposit) { this.timeDeposit = timeDeposit; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
