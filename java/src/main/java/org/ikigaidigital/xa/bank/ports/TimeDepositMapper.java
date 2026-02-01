package org.ikigaidigital.xa.bank.ports;

import org.ikigaidigital.xa.bank.adapters.persistence.TimeDepositEntity;
import org.ikigaidigital.xa.bank.web.dto.TimeDepositDto;

public class TimeDepositMapper {
    public static TimeDepositDto toDto(TimeDepositEntity e) {
        TimeDepositDto dto = new TimeDepositDto();
        dto.setId(e.getId());
        dto.setPlanType(e.getPlanType());
        dto.setBalance(e.getBalance());
        dto.setDays(e.getDays());
        dto.setWithdrawals(e.getWithdrawals() == null ? 0 : e.getWithdrawals().size());
        return dto;
    }
}
