package com.marindulja.mgmt_sys_demo_2.controllers;

import com.marindulja.mgmt_sys_demo_2.dto.RepairRequest;
import com.marindulja.mgmt_sys_demo_2.services.AccepterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acceptance")
@RequiredArgsConstructor
public class AcceptanceController {
    private final AccepterService accepterService;

    @PostMapping("/acceptProduct")
    public void accept(@RequestBody RepairRequest repairRequest) {
        accepterService.accept(repairRequest);
    }
}
