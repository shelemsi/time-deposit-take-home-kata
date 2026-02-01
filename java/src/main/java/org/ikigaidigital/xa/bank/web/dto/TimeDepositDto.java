package org.ikigaidigital.xa.bank.web.dto;

import java.math.BigDecimal;

public class TimeDepositDto {
    private Long id;
    private String planType;
    private BigDecimal balance;
    private Integer days;
    private Integer withdrawals;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    public Integer getWithdrawals() { return withdrawals; }
    public void setWithdrawals(Integer withdrawals) { this.withdrawals = withdrawals; }
}
