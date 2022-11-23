package com.marindulja.mgmt_sys_demo_2;

import com.marindulja.mgmt_sys_demo_2.models.Customer;
import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.models.RepairStatus;
import com.marindulja.mgmt_sys_demo_2.models.UserRole;
import com.marindulja.mgmt_sys_demo_2.repositories.IRepairRepository;
import com.marindulja.mgmt_sys_demo_2.repositories.ITechnicianRepository;
import com.marindulja.mgmt_sys_demo_2.services.TechnicianService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TechnicianServiceTests {

    @InjectMocks
    private TechnicianService technicianService;

    @Mock
    private ITechnicianRepository technicianRepository;

    @Mock
    private IRepairRepository repairRepository;

    @Test
    @DisplayName("Should accept a repair by a technician")
    void shouldAcceptRepair() {
        Customer customer = Customer.builder().id(1L).address("Rruga Myslym Shyri Tirane").email("test@gmail.com")
                .createdDateTime(LocalDateTime.now()).updatedDateTime(LocalDateTime.now()).phone("0684445522")
                .name("customername").surname("suranme1").fiscalCode("TST TST 97L22 Z100F").build();
        Repair repair = Repair.builder().id(3L).brand("DELL").caseNumber("9f8d73d0-d7f4-42d4-a703-69c80a6bae79")
                .createdDateTime(LocalDateTime.now())
                .dateOfPurchase(new Date(2022-07-26)).description("Problem with windows configuration")
                .serialNumber("123456789101119").status(RepairStatus.PENDING).template("test3").updatedDateTime(LocalDateTime.now())
                .warrantyExpireDate(new Date(2022-11-30)).customer(customer).build();

        when(repairRepository.findById(3L)).thenReturn(Optional.of(repair));
        repair.setStatus(RepairStatus.IN_PROGRESS);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        User userDetails = new User("1","12345", AuthorityUtils.createAuthorityList("ROLE_" + UserRole.ROLE_TECHNICIAN));
        com.marindulja.mgmt_sys_demo_2.models.User technician = com.marindulja.mgmt_sys_demo_2.models.User.builder()
                .fullName("Marin Dulja")
                .username("technician")
                .role(UserRole.ROLE_TECHNICIAN)
                .password("12345")
                .build();
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        doReturn(Optional.of(technician)).when(technicianRepository).findById(Long.valueOf(userDetails.getUsername()));
        repair.setTechnician(technician);
        technicianService.acceptRepair(3L);
        verify(repairRepository, times(1)).save(repair);
    }
}
