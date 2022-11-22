package com.marindulja.mgmt_sys_demo_2.services;

import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.repositories.IRepairRepository;
import com.marindulja.mgmt_sys_demo_2.specifications.CustomRepairRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final IRepairRepository repairRepository;

    private final CustomRepairRepository spec;


    @Override
    public String getRepairStatus(String caseNumber) {
        return repairRepository.findOne(spec.findRepairStatusByCaseNumber(caseNumber)).get().getStatus().toString();
    }

    @Override
    public void pay(String caseNumber, double money) {
        Repair repair = repairRepository.findOne(spec.byCaseNumber(caseNumber)).get();
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
