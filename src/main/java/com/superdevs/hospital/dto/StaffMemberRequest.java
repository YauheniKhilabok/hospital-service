package com.superdevs.hospital.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffMemberRequest {

    @NotNull(message = "Name must be populated")
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
