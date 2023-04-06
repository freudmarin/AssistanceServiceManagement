package com.marindulja.mgmt_sys_demo_2.services;


import com.marindulja.mgmt_sys_demo_2.models.Repair;

import java.util.List;

public interface ITechnicianService {
    List<Repair> viewPendingRepairs();

    List<Repair> viewAcceptedRepairs();

    void acceptRepair(Long repairId);

    void rejectRepair(Long repairId, String reason);

    void completeRepair(Long repairId, double price);
}
