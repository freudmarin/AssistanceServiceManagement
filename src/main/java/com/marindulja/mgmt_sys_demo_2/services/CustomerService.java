package com.marindulja.mgmt_sys_demo_2.services;

public interface CustomerService {
    String getRepairStatus(String caseNumber);
    void pay(String caseNumber, double money);
}
