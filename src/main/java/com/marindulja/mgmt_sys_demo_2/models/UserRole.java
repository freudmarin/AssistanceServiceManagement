package com.marindulja.mgmt_sys_demo_2.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_TECHNICIAN,
    ROLE_ACCEPTANCE,
    ROLE_ADMIN;
    ;

    @Override
    public String getAuthority() {
        return name();
    }

}
