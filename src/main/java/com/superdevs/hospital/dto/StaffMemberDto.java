package com.superdevs.hospital.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffMemberDto {

    private Long id;
    private String name;
    private String uuid;
    private LocalDate registrationDate;
}
