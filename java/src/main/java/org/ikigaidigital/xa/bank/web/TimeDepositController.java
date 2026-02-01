package org.ikigaidigital.xa.bank.web;

import org.ikigaidigital.xa.bank.core.TimeDepositService;
import org.ikigaidigital.xa.bank.ports.TimeDepositMapper;
import org.ikigaidigital.xa.bank.web.dto.TimeDepositDto;
import org.ikigaidigital.xa.bank.web.dto.UpdateResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/time-deposits")
public class TimeDepositController {
    private final TimeDepositService service;

    public TimeDepositController(TimeDepositService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TimeDepositDto>> getAll() {
        List<TimeDepositDto> dtos = service.findAll().stream()
                .map(TimeDepositMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<UpdateResult> updateAllBalances() {
        int updated = service.updateAllBalances();
        UpdateResult r = new UpdateResult(updated, "Balances updated successfully");
        return ResponseEntity.ok(r);
    }
}
