package com.superdevs.hospital.service.validator;

import com.superdevs.hospital.exception.InvalidUUIDException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UUIDValidator implements Validator<String> {

    @Override
    public void validate(String validatedObject) {
        try {
            UUID.fromString(validatedObject);
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage(), validatedObject);
            throw new InvalidUUIDException(ex.getMessage());
        }
    }
}
