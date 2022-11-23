package com.marindulja.mgmt_sys_demo_2.controllers;

import com.marindulja.mgmt_sys_demo_2.dto.PayDto;
import com.marindulja.mgmt_sys_demo_2.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomersController {
    private final CustomerService customerService;

    @GetMapping("/getRepairStatus/{caseNumber}")
    public String getRepairStatus(@PathVariable("caseNumber") String caseNumber) {
        return customerService.getRepairStatus(caseNumber);
    }

    @PostMapping("/pay/{caseNumber}")
    public ResponseEntity pay(@PathVariable("caseNumber") String caseNumber, @RequestBody PayDto payDto) {
        customerService.pay(caseNumber, payDto.getMoney());
        return new ResponseEntity(HttpStatus.OK);
    }
}
