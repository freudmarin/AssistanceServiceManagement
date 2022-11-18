package com.marindulja.mgmt_sys_demo_2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RepairRequest {
    private String template;
    private String repairNotes;
    private String description;
    private double price;
    private String serialNumber;
    private String brand;
    private Date dateOfPurchase;
    private Date warrantyExpireDate;
    private CustomerRequester requester;
}
