// java
package org.ikigaidigital.xa.bank.adapters.external;

import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.ikigaidigital.xa.bank.adapters.persistence.WithdrawalEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 Adapter that delegates to TimeDepositCalculator.updateBalance.
 Keeps adapter semantics-preserving and does not change any external calculator signature.
*/
@Component
public class TimeDepositCalculatorAdapter {
    public void applyUpdate(TimeDepositEntity entity, List<WithdrawalEntity> withdrawals) {
        // Delegate to the concrete calculator implementation
        TimeDepositCalculator.updateBalance(entity, withdrawals);
    }
}