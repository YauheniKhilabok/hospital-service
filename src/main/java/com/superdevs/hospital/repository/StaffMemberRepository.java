package com.superdevs.hospital.repository;

import com.superdevs.hospital.entity.StaffMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffMemberRepository extends JpaRepository<StaffMember, Long> {

    Optional<StaffMember> findByUuid(String uuid);
}
