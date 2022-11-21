package com.marindulja.mgmt_sys_demo_2.dto;

import lombok.Getter;

@Getter
public class TechWithCountDto {
    String techFullName;
    long count;

    public TechWithCountDto(String techFullName, long count) {
        this.techFullName = techFullName;
        this.count = count;
    }
}
