package com.superdevs.hospital.service.impl;

import com.superdevs.hospital.dto.PatientDto;
import com.superdevs.hospital.entity.Patient;
import com.superdevs.hospital.exception.PatientNotFoundException;
import com.superdevs.hospital.mapper.PatientMapper;
import com.superdevs.hospital.repository.PatientRepository;
import com.superdevs.hospital.service.csv.CsvBuilder;
import com.superdevs.hospital.service.PatientService;
import com.superdevs.hospital.service.validator.Validator;
import java.io.File;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultPatientService implements PatientService {

    private static final int MIN_AGE = 2;

    private final PatientRepository patientRepository;
    @Qualifier("uuidValidator")
    private final Validator uuidValidator;
    @Qualifier("dateRangeValidator")
    private final Validator dateRangeValidator;
    private final PatientMapper patientMapper;
    private final CsvBuilder csvBuilder;

    @Override
    public Page<PatientDto> getAllNonBabyStaffMemberPatients(String staffMemberUuid, int limit, int page) {
        uuidValidator.validate(staffMemberUuid);
        log.info("getAllNonBabyStaffMemberPatients with uuid: {}", staffMemberUuid);
        PageRequest pageRequest = buildPageRequest(limit, page);
        Page<Patient> patientPage = patientRepository.findByStaffMemberUuidAndAgeGreaterThan(staffMemberUuid, MIN_AGE, pageRequest);
        return patientMapper.toPatientDtoPage(patientPage);
    }

    private PageRequest buildPageRequest(int limit, int page) {
        return PageRequest.of(page - 1, limit, Sort.by(Direction.ASC, "age"));
    }

    @Override
    public File downloadPatientData(String staffMemberUuid, Long patientId) {
        uuidValidator.validate(staffMemberUuid);
        Patient patient = patientRepository.findByStaffMemberUuidAndPatientId(staffMemberUuid, patientId)
            .orElseThrow(() -> new PatientNotFoundException("Patient is not found."));
        log.info("downloadPatientData: {}", patient);
        return csvBuilder.createCsvFileWithPatientData(patient);
    }

    @Transactional
    @Override
    public void deleteStaffMemberPatientsInDateRange(String staffMemberUuid, LocalDateTime from, LocalDateTime to) {
        uuidValidator.validate(staffMemberUuid);
        dateRangeValidator.validate(from, to);
        log.info("deleteStaffMemberPatientsInDateRange with uuid: {}, fromDate: {}, toDate: {}", staffMemberUuid, from, to);
        patientRepository.deleteAllByStaffMemberUuidAndInDateRange(staffMemberUuid, from, to);
    }
}
