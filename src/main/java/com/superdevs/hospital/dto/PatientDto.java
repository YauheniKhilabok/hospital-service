package com.superdevs.hospital.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private Long id;
    private String name;
    private int age;
    private LocalDateTime lastVisitDate;
}
