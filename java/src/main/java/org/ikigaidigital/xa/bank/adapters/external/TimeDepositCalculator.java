package org.ikigaidigital.xa.bank.adapters.external;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.ikigaidigital.xa.bank.adapters.persistence.WithdrawalEntity;

/*
 Implements the monthly interest update logic according to the kata requirements:
 - No interest for the first 30 days for any plan.
 - Basic: 1% monthly.
 - Student: 3% monthly, no interest after 365 days.
 - Premium: 5% monthly, interest starts after 45 days.
 Assumption: this method applies a single monthly tick (30 days). Interest is prorated
 for the days within the 30-day window that are eligible. Withdrawals are not considered
 for interest calculation in this implementation (can be added later).
*/
public class TimeDepositCalculator {
    private static final BigDecimal RATE_BASIC = new BigDecimal("0.01");
    private static final BigDecimal RATE_STUDENT = new BigDecimal("0.03");
    private static final BigDecimal RATE_PREMIUM = new BigDecimal("0.05");

    public static void updateBalance(TimeDepositEntity entity, List<WithdrawalEntity> withdrawals) {
        if (entity == null) return;

        BigDecimal balance = entity.getBalance() == null ? BigDecimal.ZERO : entity.getBalance();
        int startDay = entity.getDays() == null ? 0 : entity.getDays();
        int endDay = startDay + 30; // simulate one monthly update of 30 days
        int eligibleDays = 0;
        String plan = entity.getPlanType() == null ? "" : entity.getPlanType().trim().toLowerCase();

        for (int day = startDay + 1; day <= endDay; day++) {
            // no interest for first 30 days
            if (day <= 30) continue;

            boolean applies = false;
            switch (plan) {
                case "student":
                    // student: interest only within first year (<= 365 days)
                    applies = day <= 365;
                    break;
                case "premium":
                    // premium: interest starts after 45 days
                    applies = day > 45;
                    break;
                default:
                    // basic and any other plan: interest after 30 days
                    applies = true;
                    break;
            }

            if (applies) eligibleDays++;
        }

        if (eligibleDays > 0 && balance.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal rate;
            switch (plan) {
                case "student": rate = RATE_STUDENT; break;
                case "premium": rate = RATE_PREMIUM; break;
                default: rate = RATE_BASIC; break;
            }

            // prorate interest for the eligible days in this 30-day window
            BigDecimal fraction = new BigDecimal(eligibleDays).divide(new BigDecimal(30), 8, RoundingMode.HALF_UP);
            BigDecimal interest = balance.multiply(rate).multiply(fraction);

            balance = balance.add(interest).setScale(4, RoundingMode.HALF_UP);
        } else {
            // keep scale consistent
            balance = balance.setScale(4, RoundingMode.HALF_UP);
        }

        entity.setBalance(balance);
        entity.setDays(endDay);
    }
}
