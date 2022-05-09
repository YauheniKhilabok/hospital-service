package com.superdevs.hospital.controller;

import com.superdevs.hospital.dto.StaffMemberRequest;
import com.superdevs.hospital.dto.StaffMemberDto;
import com.superdevs.hospital.service.StaffMemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/staff-members")
public class StaffController {

    private final StaffMemberService staffMemberService;

    @PostMapping
    public ResponseEntity<StaffMemberDto> createStaffMember(
        @RequestBody @Valid StaffMemberRequest staffMemberRequest
    ) {
        log.info("POST createStaffMember with requestBody: {}", staffMemberRequest);
        return ResponseEntity.ok(staffMemberService.createStaffMember(staffMemberRequest));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<StaffMemberDto> updateStaffMember(
        @PathVariable("uuid") String uuid,
        @RequestBody @Valid StaffMemberRequest staffMemberRequest
    ) {
        log.info("PUT updateStaffMember with uuid: {}, requestBody: {}", uuid, staffMemberRequest);
        return ResponseEntity.ok(staffMemberService.updateStaffMember(uuid, staffMemberRequest));
    }
}
