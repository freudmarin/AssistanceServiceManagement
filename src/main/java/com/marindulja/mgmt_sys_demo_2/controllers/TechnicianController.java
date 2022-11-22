package com.marindulja.mgmt_sys_demo_2.controllers;

import com.marindulja.mgmt_sys_demo_2.dto.RepairRequest;
import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.services.TechnicianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technician")
public class TechnicianController {
    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping("/viewPendingRepairs")
    public List<Repair> viewPendingRepairs() {
        return technicianService.viewPendingRepairs();
    }

    @PostMapping("/acceptRepair/{repairID}")
    public ResponseEntity accept(@PathVariable("repairID") Long repairID) {
        technicianService.acceptRepair(repairID);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/viewAcceptedRepairs")
    public List<Repair> viewAcceptedRepairs() {
        return technicianService.viewAcceptedRepairs();
    }

    @PostMapping("/completeRepair/{repairID}")
    public ResponseEntity completeRepair(@PathVariable("repairID") Long repairID, @RequestBody RepairRequest repairRequest) {
        technicianService.completeRepair(repairID, repairRequest.getPrice());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/rejectRepair/{repairID}")
    public ResponseEntity rejectRepair(@PathVariable("repairID") Long repairID, @RequestBody RepairRequest repairRequest) {
        technicianService.rejectRepair(repairID, repairRequest.getRepairNotes());
        return new ResponseEntity(HttpStatus.OK);
    }
}
