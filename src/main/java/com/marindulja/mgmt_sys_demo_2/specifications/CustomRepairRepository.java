package com.marindulja.mgmt_sys_demo_2.specifications;


import com.marindulja.mgmt_sys_demo_2.dto.TechWithCountDto;
import com.marindulja.mgmt_sys_demo_2.dto.TechnicianDto;
import com.marindulja.mgmt_sys_demo_2.models.*;
import com.marindulja.mgmt_sys_demo_2.repositories.ITechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
public class CustomRepairRepository {
    private final ITechnicianRepository technicianRepository;

    private final EntityManager entityManager;

    public Specification<Repair> byStatus(RepairStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Repair_.status), status);
    }

    public Specification<Repair> byStatus2(RepairStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Repair_.status), status);
    }

    public Specification<Repair> byCaseNumber(String caseNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Repair_.caseNumber), caseNumber);
    }

    public Specification<Repair> byStatusAndTechnicianId(RepairStatus status, Long id) {
        User technician = technicianRepository.findById(id).get();
        Specification<Repair> repairSpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Repair_.status), status), criteriaBuilder.equal(root.get(Repair_.technician).get(User_.ID), id));
        return repairSpecification;
    }

    public Specification<Repair> findRepairStatusByCaseNumber(String caseNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Repair_.caseNumber), caseNumber);
    }

    public Specification<Repair> repairByStatusAndUpdatedDateTimeBetween(RepairStatus status, LocalDateTime date1, LocalDateTime date2) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Repair_.status), status), criteriaBuilder.between(root.get(Repair_.updatedDateTime), date1, date2));
    }


    public Map<String, Long> countRepairsByEachTechnicianOverTime(LocalDateTime start, LocalDateTime end) {
//    public List<TechWithCountDto> countRepairsByEachTechnicianOverTime(LocalDateTime start, LocalDateTime end) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        //        final CriteriaQuery<TechWithCountDto> query = criteriaBuilder.createQuery(TechWithCountDto.class);
        final Root<Repair> root = query.from(Repair.class);
        final Path<String> expression = root.get(Repair_.technician).get(User_.fullName);
        query.multiselect(expression, criteriaBuilder.count(root));
        query.select(criteriaBuilder.tuple(expression, criteriaBuilder.count(root)));
        query.where(criteriaBuilder.between(root.get(Repair_.updatedDateTime), start, end));
        query.groupBy(expression);
        final List<Tuple> resultList = entityManager.createQuery(query).getResultList();
        return resultList.stream()
                .collect(toMap(
                        t -> t.get(0, String.class),
                        t -> t.get(1, Long.class))
                );
    }


    public List<TechWithCountDto> countRepairsByEachTechnicianOverTime2(LocalDateTime start, LocalDateTime end) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TechWithCountDto> query = criteriaBuilder.createQuery(TechWithCountDto.class);
        //        final CriteriaQuery<TechWithCountDto> query = criteriaBuilder.createQuery(TechWithCountDto.class);
        final Root<Repair> root = query.from(Repair.class);
        final Path<String> expression = root.get(Repair_.technician).get(User_.fullName);
        query.multiselect(expression, criteriaBuilder.count(root));
        query.where(criteriaBuilder.between(root.get(Repair_.updatedDateTime), start, end));
        query.groupBy(expression);
        return entityManager.createQuery(query).getResultList();
    }

    public List<TechnicianDto> allTechsWithMoreThan3Repairs(LocalDateTime start, LocalDateTime end) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<TechnicianDto> criteriaQuery = criteriaBuilder.createQuery(TechnicianDto.class);
        Root<User> techRoot = criteriaQuery.from(User.class);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Repair> subqueryRoot = subquery.from(Repair.class);
        Expression<Long> techsCount = criteriaBuilder.count(subqueryRoot.get(Repair_.technician));
        subquery.select(subqueryRoot.get(Repair_.technician).get(User_.ID))
                .where(criteriaBuilder.equal(subqueryRoot.get(Repair_.technician), techRoot.get(User_.id)),
                        criteriaBuilder.between(subqueryRoot.get(Repair_.updatedDateTime), start, end))
                .groupBy(subqueryRoot.get(Repair_.technician))
                .having(criteriaBuilder.gt(techsCount, 3));

        criteriaQuery.select(criteriaBuilder.construct(
                TechnicianDto.class,
                techRoot.get(User_.fullName),
                techRoot.get(User_.username),
                techRoot.get(User_.password)
        )).where(criteriaBuilder.exists(subquery));
        List<TechnicianDto> techList = this.entityManager.createQuery(criteriaQuery)
                .getResultList();
        return techList;

    }

    public List<TechnicianDto> allTechsWithMoreThan3RepairsWithCount(LocalDateTime start, LocalDateTime end) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<TechnicianDto> criteriaQuery = criteriaBuilder.createQuery(TechnicianDto.class);
        Root<Repair> repairRoot = criteriaQuery.from(Repair.class);
        Join<Repair, User> repairUserJoin = repairRoot.join(Repair_.technician);
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Repair> subqueryRoot = subquery.from(Repair.class);
        Expression<Long> techsCount = criteriaBuilder.count(subqueryRoot.get(Repair_.technician));
        subquery.select(subqueryRoot.get(Repair_.technician).get(User_.ID))
                .where(criteriaBuilder.equal(subqueryRoot.get(Repair_.technician), repairUserJoin.get(User_.id)),
                        criteriaBuilder.between(subqueryRoot.get(Repair_.updatedDateTime), start, end))
                .groupBy(subqueryRoot.get(Repair_.technician))
                .having(criteriaBuilder.gt(techsCount, 1));

        criteriaQuery.select(criteriaBuilder.construct(
                TechnicianDto.class,
                repairUserJoin.get(User_.fullName),
                repairUserJoin.get(User_.username),
                repairUserJoin.get(User_.password),
                criteriaBuilder.count(repairUserJoin.get(Repair_.ID))
        )).where(criteriaBuilder.exists(subquery)).groupBy(repairUserJoin.get(User_.fullName), repairUserJoin.get(User_.username), repairUserJoin.get(User_.password));
        List<TechnicianDto> techList = this.entityManager.createQuery(criteriaQuery)
                .getResultList();
        return techList;

    }
}
