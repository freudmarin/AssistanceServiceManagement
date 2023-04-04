package com.marindulja.mgmt_sys_demo_2.controllers;

import com.marindulja.mgmt_sys_demo_2.dto.RepairRequest;
import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technician")
@RequiredArgsConstructor
public class TechnicianController {
    private final TechnicianService technicianService;
    @GetMapping("/viewPendingRepairs")
    public List<Repair> viewPendingRepairs() {
        return technicianService.viewPendingRepairs();
    }

    @PostMapping("/acceptRepair/{repairID}")
    public ResponseEntity<HttpStatus> accept(@PathVariable("repairID") Long repairID) {
        technicianService.acceptRepair(repairID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/viewAcceptedRepairs")
    public List<Repair> viewAcceptedRepairs() {
        return technicianService.viewAcceptedRepairs();
    }

    @PostMapping("/completeRepair/{repairID}")
    public ResponseEntity<HttpStatus> completeRepair(@PathVariable("repairID") Long repairID, @RequestBody RepairRequest repairRequest) {
        technicianService.completeRepair(repairID, repairRequest.getPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rejectRepair/{repairID}")
    public ResponseEntity<HttpStatus> rejectRepair(@PathVariable("repairID") Long repairID, @RequestBody RepairRequest repairRequest) {
        technicianService.rejectRepair(repairID, repairRequest.getRepairNotes());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
