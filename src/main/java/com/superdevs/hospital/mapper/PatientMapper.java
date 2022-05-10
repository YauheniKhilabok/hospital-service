package com.superdevs.hospital.mapper;

import com.superdevs.hospital.dto.PatientDto;
import com.superdevs.hospital.entity.Patient;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    default Page<PatientDto> toPatientDtoPage(Page<Patient> patientPage) {
        List<PatientDto> patientDtos = patientPage.getContent().stream()
            .map(this::toPatientDto)
            .collect(Collectors.toList());
        return new PageImpl<>(patientDtos);
    }

    PatientDto toPatientDto(Patient patient);
}
