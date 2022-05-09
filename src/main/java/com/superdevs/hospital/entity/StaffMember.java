package com.superdevs.hospital.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff_members")
public class StaffMember extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private LocalDate registrationDate;
}
