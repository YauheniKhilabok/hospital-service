package com.superdevs.hospital.repository;

import com.superdevs.hospital.entity.Patient;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(
        value = "SELECT p FROM Patient p JOIN StaffMember sm ON sm.id = p.staffMember.id "
            + "WHERE sm.uuid = :staffMemberUuid and p.age > :age"
    )
    Page<Patient> findByStaffMemberUuidAndAgeGreaterThan(@Param("staffMemberUuid") String staffMemberUuid, @Param("age") int age,
        Pageable pageable);

    @Query(
        value = "SELECT p FROM Patient p JOIN StaffMember sm ON sm.id = p.staffMember.id "
            + "WHERE sm.uuid = :staffMemberUuid and p.id = :patientId"
    )
    Optional<Patient> findByStaffMemberUuidAndPatientId(@Param("staffMemberUuid") String staffMemberUuid, @Param("patientId") Long patientId);

    @Modifying
    @Query(value = "DELETE FROM patients WHERE patients.staff_member_id = ("
        + "SELECT sm.id FROM staff_members sm WHERE sm.uuid = :staffMemberUuid"
        + ") AND patients.last_visit_date BETWEEN :from AND :to",
        nativeQuery = true)
    void deleteAllByStaffMemberUuidAndInDateRange(@Param("staffMemberUuid") String staffMemberUuid,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to);
}
