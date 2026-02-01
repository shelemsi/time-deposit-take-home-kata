package org.ikigaidigital.xa.bank.ports;

import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.ikigaidigital.xa.bank.adapters.persistence.WithdrawalEntity;
import org.ikigaidigital.xa.bank.web.dto.TimeDepositDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDepositMapperTest {

    @Test
    void toDto_maps_fields_and_counts_withdrawals() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setId(5L);
        e.setPlanType("Student");
        e.setBalance(new BigDecimal("123.4500"));
        e.setDays(100);

        WithdrawalEntity w1 = new WithdrawalEntity();
        WithdrawalEntity w2 = new WithdrawalEntity();
        e.setWithdrawals(List.of(w1, w2));

        TimeDepositDto dto = TimeDepositMapper.toDto(e);

        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getPlanType()).isEqualTo("Student");
        assertThat(dto.getBalance()).isEqualByComparingTo(new BigDecimal("123.4500"));
        assertThat(dto.getDays()).isEqualTo(100);
        assertThat(dto.getWithdrawals()).isEqualTo(2);
    }
}
