package com.marindulja.mgmt_sys_demo_2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String fiscalCode;
}
