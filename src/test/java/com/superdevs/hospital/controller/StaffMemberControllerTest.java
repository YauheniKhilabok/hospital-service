package com.superdevs.hospital.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.superdevs.hospital.controller.handler.FieldErrorResponse;
import com.superdevs.hospital.dto.StaffMemberDto;
import com.superdevs.hospital.dto.StaffMemberRequest;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class StaffMemberControllerTest {

    private static final String PERMANENT_URL_PART = "http://localhost:8081/api/v1/staff-members";

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void createStaffMember_validName_ok() {
        StaffMemberRequest staffMemberRequest = buildStaffMemberRequest("Jack Jackson");

        ResponseEntity<StaffMemberDto> staffMemberResponse = restTemplate.postForEntity(PERMANENT_URL_PART, staffMemberRequest, StaffMemberDto.class);
        StaffMemberDto staffMember = staffMemberResponse.getBody();

        assertEquals(HttpStatus.OK, staffMemberResponse.getStatusCode());
        assertNotNull(staffMember);
        assertNotNull(staffMember.getId());
        assertNotNull(staffMember.getUuid());
        assertNotNull(staffMember.getRegistrationDate());
        assertEquals("Jack Jackson", staffMember.getName());
    }

    @Test
    public void createStaffMember_emptyName_badRequest() {
        StaffMemberRequest staffMemberRequest = buildStaffMemberRequest("");

        assertThrows(HttpClientErrorException.BadRequest.class,
            () -> restTemplate.postForEntity(PERMANENT_URL_PART, staffMemberRequest, FieldErrorResponse.class));
    }

    @Test
    public void createStaffMember_nullName_badRequest() {
        StaffMemberRequest staffMemberRequest = buildStaffMemberRequest(null);

        assertThrows(HttpClientErrorException.BadRequest.class,
            () -> restTemplate.postForEntity(PERMANENT_URL_PART, staffMemberRequest, FieldErrorResponse.class));
    }

    @Test
    public void updateStaffMember_validName_ok() {
        StaffMemberRequest createStaffMemberRequest = buildStaffMemberRequest("Jack Jackson New");
        ResponseEntity<StaffMemberDto> createStaffMemberResponse = restTemplate.postForEntity(PERMANENT_URL_PART, createStaffMemberRequest,
            StaffMemberDto.class);
        StaffMemberDto createdStaffMember = createStaffMemberResponse.getBody();
        StaffMemberRequest updateStaffMemberRequest = buildStaffMemberRequest("Updated name");

        restTemplate.put(PERMANENT_URL_PART + "/" + Objects.requireNonNull(createdStaffMember).getUuid(), updateStaffMemberRequest,
            StaffMemberDto.class);
    }

    @Test
    public void updateStaffMember_emptyName_badRequest() {
        StaffMemberRequest createStaffMemberRequest = buildStaffMemberRequest("Jack Jackson New");
        ResponseEntity<StaffMemberDto> createStaffMemberResponse = restTemplate.postForEntity(PERMANENT_URL_PART, createStaffMemberRequest,
            StaffMemberDto.class);
        StaffMemberDto createdStaffMember = createStaffMemberResponse.getBody();
        StaffMemberRequest updateStaffMemberRequest = buildStaffMemberRequest("");

        assertThrows(HttpClientErrorException.BadRequest.class,
            () -> restTemplate.put(PERMANENT_URL_PART + "/" + Objects.requireNonNull(createdStaffMember).getUuid(), updateStaffMemberRequest,
                StaffMemberDto.class));
    }

    @Test
    public void updateStaffMember_nullName_badRequest() {
        StaffMemberRequest createStaffMemberRequest = buildStaffMemberRequest("Jack Jackson New");
        ResponseEntity<StaffMemberDto> createStaffMemberResponse = restTemplate.postForEntity(PERMANENT_URL_PART, createStaffMemberRequest,
            StaffMemberDto.class);
        StaffMemberDto createdStaffMember = createStaffMemberResponse.getBody();
        StaffMemberRequest updateStaffMemberRequest = buildStaffMemberRequest(null);

        assertThrows(HttpClientErrorException.BadRequest.class,
            () -> restTemplate.put(PERMANENT_URL_PART + "/" + Objects.requireNonNull(createdStaffMember).getUuid(), updateStaffMemberRequest,
                StaffMemberDto.class));
    }

    @Test
    public void updateStaffMember_invalidUUID_badRequest() {
        StaffMemberRequest updateStaffMemberRequest = buildStaffMemberRequest("Valid Name");

        assertThrows(HttpClientErrorException.BadRequest.class,
            () -> restTemplate.put(PERMANENT_URL_PART + "/" + "qwer", updateStaffMemberRequest,
                StaffMemberDto.class));
    }

    @Test
    public void updateStaffMember_staffMemberUuidIsNotFound_notFound() {
        StaffMemberRequest updateStaffMemberRequest = buildStaffMemberRequest("Valid Name");

        assertThrows(HttpClientErrorException.NotFound.class,
            () -> restTemplate.put(PERMANENT_URL_PART + "/" + "0bc98269-a05a-11ec-949b-0242ac130002", updateStaffMemberRequest,
                StaffMemberDto.class));
    }

    private StaffMemberRequest buildStaffMemberRequest(String name) {
        return StaffMemberRequest.builder()
            .name(name)
            .build();
    }
}
