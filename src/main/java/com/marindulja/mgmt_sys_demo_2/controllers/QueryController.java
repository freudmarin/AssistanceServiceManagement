package com.marindulja.mgmt_sys_demo_2.controllers;

import com.marindulja.mgmt_sys_demo_2.models.RepairStatus;
import com.marindulja.mgmt_sys_demo_2.repositories.IRepairRepository;
import com.marindulja.mgmt_sys_demo_2.specifications.CustomRepairRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
@Log4j2
public class QueryController {
    private final IRepairRepository repairRepository;

    private final CustomRepairRepository spec;

    @GetMapping(value = "/totalNumberOfCompletedRepairsBetween")
    public Long  totalNumberOfCompletedRepairsOverPeriodOfTime(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                                @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("Executing query");

        return repairRepository.count(spec.repairByStatusAndUpdatedDateTimeBetween(RepairStatus.COMPLETED, start, end));
    }

    @GetMapping(value = "/totalNumberOfRejectedRepairsBetween")
    public Integer totalNumberOfRejectedRepairsOverPeriodOfTime(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return repairRepository.countRepairByStatusAndUpdatedDateTimeBetween(RepairStatus.CANCELED, start, end);
    }

    @GetMapping(value = "/numberOfRepairsByEachTechnician")
    public List<Object> numberOfRepairsProcessedByEachTechnicianOverPeriodOfTime(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return repairRepository.countRepairsByEachTechnicianOverTime(start, end);
    }
}
