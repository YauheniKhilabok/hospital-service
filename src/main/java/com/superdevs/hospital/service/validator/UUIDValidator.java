package com.superdevs.hospital.service.validator;

import com.superdevs.hospital.exception.InvalidUUIDException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("uuidValidator")
public class UUIDValidator implements Validator {

    @Override
    public void validate(Object... validatedObjects) {
        if (validatedObjects[0] instanceof String uuid) {
            try {
                UUID.fromString(uuid);
            } catch (IllegalArgumentException ex) {
                log.error(ex.getMessage(), ex);
                throw new InvalidUUIDException(ex.getMessage());
            }
        }
    }
}
