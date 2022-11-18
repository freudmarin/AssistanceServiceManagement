package com.marindulja.mgmt_sys_demo_2.dto;


import com.marindulja.mgmt_sys_demo_2.models.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private UserRole role;

}
