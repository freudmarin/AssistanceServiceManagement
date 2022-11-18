package com.marindulja.mgmt_sys_demo_2;

import com.marindulja.mgmt_sys_demo_2.dto.UserDto;
import com.marindulja.mgmt_sys_demo_2.models.User;
import com.marindulja.mgmt_sys_demo_2.models.UserRole;
import com.marindulja.mgmt_sys_demo_2.repositories.IUserRepository;
import com.marindulja.mgmt_sys_demo_2.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private IUserRepository userRepository;

    @Test
    @DisplayName("Should Save a new user")
    void shouldSaveUser() {
       UserDto user = UserDto.builder()
                .fullName("Marin Dulja")
                .username("acceptance 1")
                .role(UserRole.ROLE_ACCEPTANCE)
                .password("12345")
                .build();
        User userMocked = User.builder().id(1L).fullName("Marin Dulja").username("acceptance 1").role(UserRole.ROLE_ACCEPTANCE)
                .password("12345").build();
        when(userRepository.save(any(User.class))).thenReturn(userMocked);
        UserDto userDtoSaved =  userDetailsService.addUser(user);
        assertNotNull(userDtoSaved);
        assertEquals(userMocked.getFullName(), userDtoSaved.getFullName());
        assertEquals(userMocked.getUsername(), userDtoSaved.getUsername());
        assertEquals(userMocked.getPassword(), userDtoSaved.getPassword());
        assertEquals(userMocked.getRole(),userDtoSaved.getRole());
    }
}
