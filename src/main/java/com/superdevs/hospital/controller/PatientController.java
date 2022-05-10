package com.superdevs.hospital.controller;

import com.superdevs.hospital.dto.PatientDto;
import com.superdevs.hospital.service.PatientService;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/staff-members/{uuid}/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<Page<PatientDto>> getAllNonBabyStaffMemberPatients(
        @PathVariable("uuid") String staffMemberUuid,
        @RequestParam(value = "limit", defaultValue = "25", required = false) int limit,
        @RequestParam(value = "page", defaultValue = "1", required = false) int page
    ) {
        log.info("GET getAllNonBabyStaffMemberPatients with uuid: {}, limit: {}, page: {}", staffMemberUuid, limit, page);
        return ResponseEntity.ok(patientService.getAllNonBabyStaffMemberPatients(staffMemberUuid, limit, page));
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadPatientData(
        @PathVariable("uuid") String staffMemberUuid,
        @PathVariable("id") Long patientId
    ) {
        log.info("GET downloadPatientData with staffMemberUuid: {} and patientId: {}", staffMemberUuid, patientId);
        File file = patientService.downloadPatientData(staffMemberUuid, patientId);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
            .contentLength(file.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStaffMemberPatientsInDateRange(
        @PathVariable("uuid") String staffMemberUuid,
        @RequestParam(value = "fromDate")
        @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime from,
        @RequestParam(value = "toDate")
        @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime to
    ) {
        log.info("DELETE deleteStaffMemberPatientsInDateRange with uuid: {}, fromDate: {}, toDate: {}", staffMemberUuid, from, to);
        patientService.deleteStaffMemberPatientsInDateRange(staffMemberUuid, from, to);
        return ResponseEntity.noContent().build();
    }
}
