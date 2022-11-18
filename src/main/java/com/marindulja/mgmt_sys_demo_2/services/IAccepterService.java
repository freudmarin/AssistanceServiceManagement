package com.marindulja.mgmt_sys_demo_2.services;

import com.marindulja.mgmt_sys_demo_2.dto.RepairRequest;

public interface IAccepterService {
    void accept(RepairRequest request);
}
