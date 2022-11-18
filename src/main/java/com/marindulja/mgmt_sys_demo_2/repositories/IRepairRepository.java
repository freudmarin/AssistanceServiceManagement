package  com.marindulja.mgmt_sys_demo_2.repositories;;


import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.models.RepairStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IRepairRepository extends JpaRepository<Repair, Long>, JpaSpecificationExecutor<Repair> {

    List<Repair> findByTechnicianId(Long id);

    List<Repair> findByStatus(RepairStatus status);

    Repair findByCaseNumber(String caseNumber);

    List<Repair> findByStatusAndTechnicianId(RepairStatus status, Long id);

    @Query("SELECT r.status FROM Repair r WHERE r.caseNumber = :caseNumber")
    String findRepairStatusByCaseNumber(@Param("caseNumber") String caseNumber);

    Integer countRepairByStatusAndUpdatedDateTimeBetween(RepairStatus status, LocalDateTime date1, LocalDateTime date2);

    @Query("SELECT  r.technician.fullName , COUNT(r.id) FROM Repair AS r where r.updatedDateTime between :date1 and :date2 GROUP BY r.technician.fullName")
    List<Object> countRepairsByEachTechnicianOverTime(@Param("date1") LocalDateTime date1 , @Param("date2") LocalDateTime date2);
}
