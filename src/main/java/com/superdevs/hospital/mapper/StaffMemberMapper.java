package com.superdevs.hospital.mapper;

import com.superdevs.hospital.dto.StaffMemberRequest;
import com.superdevs.hospital.dto.StaffMemberDto;
import com.superdevs.hospital.entity.StaffMember;
import java.time.LocalDate;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {LocalDate.class, UUID.class})
public interface StaffMemberMapper {

    @Mapping(target = "uuid", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "registrationDate", expression = "java(LocalDate.now())")
    StaffMember toStaffMember(StaffMemberRequest staffMemberRequest);

    StaffMemberDto toStaffMemberDto(StaffMember staffMember);
}
