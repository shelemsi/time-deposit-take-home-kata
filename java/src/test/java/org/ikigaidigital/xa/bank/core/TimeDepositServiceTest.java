package org.ikigaidigital.xa.bank.core;

import org.ikigaidigital.xa.bank.adapters.external.TimeDepositCalculatorAdapter;
import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositRepository;
import org.ikigaidigital.xa.bank.adapters.persistence.WithdrawalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TimeDepositServiceTest {

    @Mock
    private TimeDepositRepository timeDepositRepository;

    @Mock
    private WithdrawalRepository withdrawalRepository;

    @Mock
    private TimeDepositCalculatorAdapter calculatorAdapter;

    @InjectMocks
    private TimeDepositService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_delegates_to_repository() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setId(1L);
        when(timeDepositRepository.findAll()).thenReturn(List.of(e));

        List<TimeDepositEntity> result = service.findAll();

        assertThat(result).hasSize(1).contains(e);
        verify(timeDepositRepository).findAll();
    }

    @Test
    void updateAllBalances_calls_adapter_and_saves_entities() {
        TimeDepositEntity e = new TimeDepositEntity();
        e.setId(1L);
        e.setBalance(new BigDecimal("200.0000"));
        e.setDays(31);

        when(timeDepositRepository.findAll()).thenReturn(List.of(e));
        when(withdrawalRepository.findByTimeDepositId(1L)).thenReturn(Collections.emptyList());

        // simulate adapter updating the entity
        doAnswer(invocation -> {
            TimeDepositEntity ent = invocation.getArgument(0);
            ent.setBalance(new BigDecimal("202.0000"));
            ent.setDays(ent.getDays() + 30);
            return null;
        }).when(calculatorAdapter).applyUpdate(eq(e), anyList());

        int updated = service.updateAllBalances();

        assertThat(updated).isEqualTo(1);
        verify(calculatorAdapter).applyUpdate(eq(e), anyList());
        verify(timeDepositRepository).save(argThat(saved -> saved.getBalance().compareTo(new BigDecimal("202.0000")) == 0));
    }
}
