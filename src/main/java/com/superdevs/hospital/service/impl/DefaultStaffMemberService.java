package com.superdevs.hospital.service.impl;

import com.superdevs.hospital.dto.StaffMemberRequest;
import com.superdevs.hospital.dto.StaffMemberDto;
import com.superdevs.hospital.entity.StaffMember;
import com.superdevs.hospital.exception.StaffMemberNotFoundException;
import com.superdevs.hospital.mapper.StaffMemberMapper;
import com.superdevs.hospital.repository.StaffMemberRepository;
import com.superdevs.hospital.service.StaffMemberService;
import com.superdevs.hospital.service.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultStaffMemberService implements StaffMemberService {

    private final StaffMemberRepository staffMemberRepository;
    private final StaffMemberMapper staffMemberMapper;
    @Qualifier("uuidValidator")
    private final Validator uuidValidator;

    @Transactional
    @Override
    public StaffMemberDto createStaffMember(StaffMemberRequest staffMemberRequest) {
        log.info("createStaffMember with request: {}", staffMemberRequest);
        StaffMember staffMemberToSave = staffMemberMapper.toStaffMember(staffMemberRequest);
        log.info("staffMemberToSave: {}", staffMemberToSave);
        StaffMember staffMemberSaved = staffMemberRepository.save(staffMemberToSave);
        return staffMemberMapper.toStaffMemberDto(staffMemberSaved);
    }

    @Transactional
    @Override
    public StaffMemberDto updateStaffMember(String uuid, StaffMemberRequest staffMemberRequest) {
        log.info("updateStaffMember with uuid: {} and request: {}", uuid, staffMemberRequest);
        uuidValidator.validate(uuid);
        StaffMember staffMemberToUpdate = staffMemberRepository.findByUuid(uuid)
            .orElseThrow(() -> new StaffMemberNotFoundException("Staff member is not found with uuid: " + uuid));
        staffMemberToUpdate.setName(staffMemberRequest.getName());
        log.info("staffMemberToUpdate: {}", staffMemberToUpdate);
        StaffMember updatedStaffMember = staffMemberRepository.save(staffMemberToUpdate);
        return staffMemberMapper.toStaffMemberDto(updatedStaffMember);
    }
}
