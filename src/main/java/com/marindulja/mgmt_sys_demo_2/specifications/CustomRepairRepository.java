package com.marindulja.mgmt_sys_demo_2.specifications;


import com.marindulja.mgmt_sys_demo_2.models.Repair;
import com.marindulja.mgmt_sys_demo_2.models.RepairStatus;
import com.marindulja.mgmt_sys_demo_2.models.User;
import com.marindulja.mgmt_sys_demo_2.repositories.ITechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomRepairRepository {
    private final ITechnicianRepository technicianRepository;

    public Specification<Repair> byStatus(RepairStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public Specification<Repair> byStatus2(RepairStatus status) {
        return new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }

    public Specification<Repair> byCaseNumber(String caseNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("caseNumber"), caseNumber);
    }

    public Specification<Repair> byStatusAndTechnicianId(RepairStatus status, Long id) {
        User technician = technicianRepository.findById(id).get();
        Specification<Repair> repairSpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status), criteriaBuilder.equal(root.get("technician"), technician));
        return repairSpecification;
    }

    public Specification<Repair> findRepairStatusByCaseNumber(String caseNumber) {
        return new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("caseNumber"), caseNumber);
            }
        };
    }

    public Specification<Repair> repairByStatusAndUpdatedDateTimeBetween(RepairStatus status, LocalDateTime date1, LocalDateTime date2) {
        return new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status), criteriaBuilder.between(root.get("updatedDateTime"), date1, date2));
            }
        };
    }

    public Specification<Repair> repairsByEachTechnicianOverTime(LocalDateTime date1, LocalDateTime date2) {
        return new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.between(root.get("updatedDateTime"), date1, date2));
                query.select(root.get("technician"));
                query.groupBy(root.get("technician"));
                return predicate;
            }
        };
    }
}
