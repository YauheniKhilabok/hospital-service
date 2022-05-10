package com.superdevs.hospital.service;

import com.superdevs.hospital.dto.PatientDto;
import java.io.File;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public interface PatientService {

    Page<PatientDto> getAllNonBabyStaffMemberPatients(String staffMemberUuid, int limit, int page);

    File downloadPatientData(String staffMemberUuid, Long patientId);

    void deleteStaffMemberPatientsInDateRange(String staffMemberUuid, LocalDateTime from, LocalDateTime to);
}
