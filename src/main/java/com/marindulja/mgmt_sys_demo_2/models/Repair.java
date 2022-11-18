package com.marindulja.mgmt_sys_demo_2.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "repairs")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_id", nullable = false)
    private Long id;

    @Column(name = "template", nullable = false)
    private String template;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "purchase_date", nullable = false)
    private Date dateOfPurchase;

    @Column(name = "warranty_expire_date", nullable = false)
    private Date warrantyExpireDate;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "repair_notes", nullable = true)
    private String repairNotes;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "status", nullable = false)
    private RepairStatus status;


    @Column(name = "case_number", nullable = false)
    private String caseNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User technician;    // Handle user role in code or divide users into two separate entities (here is handled in code)

    // ... extra fields

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Repair repair = (Repair) o;
        return id != null && Objects.equals(id, repair.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
