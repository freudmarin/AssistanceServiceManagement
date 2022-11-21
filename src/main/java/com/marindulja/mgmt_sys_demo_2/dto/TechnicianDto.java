package com.marindulja.mgmt_sys_demo_2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TechnicianDto {
    private String username;
    private String password;
    private String fullName;
    private Long count;
}
