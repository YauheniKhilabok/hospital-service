package com.superdevs.hospital.service.validator;

import com.superdevs.hospital.exception.InvalidDateRangeException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("dateRangeValidator")
public class DateRangeValidator implements Validator {

    @Override
    public void validate(Object... validatedObjects) {
        if (validatedObjects[0] instanceof LocalDateTime from && validatedObjects[1] instanceof LocalDateTime to) {
            if (from.isAfter(to)) {
                throw new InvalidDateRangeException("From date cannot be after To date.");
            }
        }
    }
}
