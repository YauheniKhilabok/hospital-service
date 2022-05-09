package com.superdevs.hospital.service;

import com.superdevs.hospital.dto.StaffMemberRequest;
import com.superdevs.hospital.dto.StaffMemberDto;

public interface StaffMemberService {

    StaffMemberDto createStaffMember(StaffMemberRequest createStaffMemberRequest);

    StaffMemberDto updateStaffMember(String uuid, StaffMemberRequest staffMemberRequest);
}
