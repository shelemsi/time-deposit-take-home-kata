package org.ikigaidigital.xa.bank.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity, Long> {
    List<WithdrawalEntity> findByTimeDepositId(Long timeDepositId);
}
