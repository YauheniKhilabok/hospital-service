package com.superdevs.hospital.service.validator;

public interface Validator<T> {

    void validate(T validatedObject);
}
