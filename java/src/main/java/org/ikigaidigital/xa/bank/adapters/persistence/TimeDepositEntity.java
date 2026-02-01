package org.ikigaidigital.xa.bank.adapters.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "timeDeposits")
public class TimeDepositEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String planType;

    @Column(nullable = false)
    private Integer days;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @OneToMany(mappedBy = "timeDeposit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WithdrawalEntity> withdrawals;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public List<WithdrawalEntity> getWithdrawals() { return withdrawals; }
    public void setWithdrawals(List<WithdrawalEntity> withdrawals) { this.withdrawals = withdrawals; }
}
