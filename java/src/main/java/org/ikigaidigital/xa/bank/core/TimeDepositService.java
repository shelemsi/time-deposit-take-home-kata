package org.ikigaidigital.xa.bank.core;

import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositRepository;
import org.ikigaidigital.xa.bank.adapters.persistence.WithdrawalEntity;
import org.ikigaidigital.xa.bank.adapters.persistence.WithdrawalRepository;
import org.ikigaidigital.xa.bank.adapters.external.TimeDepositCalculatorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimeDepositService {
    private final TimeDepositRepository timeDepositRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final TimeDepositCalculatorAdapter calculatorAdapter;

    public TimeDepositService(TimeDepositRepository timeDepositRepository,
                              WithdrawalRepository withdrawalRepository,
                              TimeDepositCalculatorAdapter calculatorAdapter) {
        this.timeDepositRepository = timeDepositRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.calculatorAdapter = calculatorAdapter;
    }

    @Transactional(readOnly = true)
    public List<TimeDepositEntity> findAll() {
        return timeDepositRepository.findAll();
    }

    @Transactional
    public int updateAllBalances() {
        List<TimeDepositEntity> all = timeDepositRepository.findAll();
        int updated = 0;
        for (TimeDepositEntity e : all) {
            List<WithdrawalEntity> w = withdrawalRepository.findByTimeDepositId(e.getId());
            // delegate to adapter which must call existing TimeDepositCalculator.updateBalance
            // adapter must update the entity's balance (or return the new balance).
            calculatorAdapter.applyUpdate(e, w);

            // persist changes (adapter should have set e.balance appropriately)
            timeDepositRepository.save(e);
            updated++;
        }
        return updated;
    }
}
