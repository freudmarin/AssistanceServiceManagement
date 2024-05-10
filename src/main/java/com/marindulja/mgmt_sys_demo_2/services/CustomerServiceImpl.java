package com.marindulja.mgmt_sys_demo_2.services;

import com.marindulja.mgmt_sys_demo_2.exception.ResourceNotFoundException;
import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.repositories.IRepairRepository;
import com.marindulja.mgmt_sys_demo_2.specifications.CustomRepairRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final IRepairRepository repairRepository;

    private final CustomRepairRepository spec;


    @Override
    public String getRepairStatus(String caseNumber) {
        Optional<Repair> repairOpt = repairRepository.findOne(spec.findRepairStatusByCaseNumber(caseNumber));
        if (repairOpt.isPresent()) {
            return repairOpt.get().getStatus().toString();
        }
        throw new ResourceNotFoundException("The repair was not found");
    }


    @Override
    public void pay(String caseNumber, double money) {
        Optional<Repair> repairOpt = repairRepository.findOne(spec.byCaseNumber(caseNumber));
        if (repairOpt.isPresent()) {
            Repair repair = repairOpt.get();
            Double price = repair.getPrice();
            if (money - repair.getPrice() >= 0) {
                repair.setPrice(0.00);
                log.info("We owe you: " + (money - price) + " money");
            } else {
                log.warn("Please, put the correct amount of money");
            }
            repairRepository.save(repair);
        }
    }
}
