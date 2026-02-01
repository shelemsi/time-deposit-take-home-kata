package org.ikigaidigital.xa.bank.adapters.external;

import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDepositCalculatorTest {

    @Test
    void basic_plan_after_30_days_applies_full_monthly_interest() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setPlanType("Basic");
        e.setBalance(new BigDecimal("1000.0000"));
        e.setDays(31); // already past first 30 days

        TimeDepositCalculator.updateBalance(e, Collections.emptyList());

        // 1% monthly on 1000 -> +10.0000
        assertThat(e.getBalance()).isEqualByComparingTo(new BigDecimal("1010.0000"));
        assertThat(e.getDays()).isEqualTo(61);
    }

    @Test
    void student_plan_stops_after_one_year_prorated() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setPlanType("Student");
        e.setBalance(new BigDecimal("1000.0000"));
        e.setDays(340); // days 341..370 -> eligible only up to day 365 => 25 days

        TimeDepositCalculator.updateBalance(e, Collections.emptyList());

        // interest = 1000 * 0.03 * (25/30) = 25.0000
        assertThat(e.getBalance()).isEqualByComparingTo(new BigDecimal("1025.0000"));
        assertThat(e.getDays()).isEqualTo(370);
    }

    @Test
    void premium_plan_interest_starts_after_45_days_prorated() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setPlanType("Premium");
        e.setBalance(new BigDecimal("1000.0000"));
        e.setDays(40); // days 41..70 -> eligible days 46..70 => 25 days

        TimeDepositCalculator.updateBalance(e, Collections.emptyList());

        // interest = 1000 * 0.05 * (25/30) = 41.666666... -> scaled to 4 decimals 41.6667
        assertThat(e.getBalance()).isEqualByComparingTo(new BigDecimal("1041.6667"));
        assertThat(e.getDays()).isEqualTo(70);
    }

    @Test
    void no_interest_for_first_30_days_partial_after() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setPlanType("Basic");
        e.setBalance(new BigDecimal("1500.0000"));
        e.setDays(10); // window 11..40 -> interest only for days 31..40 => 10 days

        TimeDepositCalculator.updateBalance(e, Collections.emptyList());

        // interest = 1500 * 0.01 * (10/30) = 5.0000
        assertThat(e.getBalance()).isEqualByComparingTo(new BigDecimal("1505.0000"));
        assertThat(e.getDays()).isEqualTo(40);
    }
}