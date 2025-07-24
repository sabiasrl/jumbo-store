package com.jumbo.stores.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validator = null;
    }

    @Test
    void whenValidStoreDto_thenNoViolations() {
        StoreDto dto = new StoreDto("Amsterdam Store", 52.0, 5.0, 10.0);
        Set<ConstraintViolation<StoreDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenBlankAddress_thenViolation() {
        StoreDto dto = new StoreDto("  ", 52.0, 5.0, 10.0);
        Set<ConstraintViolation<StoreDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("addressName");
    }

    @Test
    void whenLatitudeOutOfRange_thenViolation() {
        StoreDto dto = new StoreDto("Store", 100.0, 5.0, 10.0);
        Set<ConstraintViolation<StoreDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("latitude");
    }

    @Test
    void whenLongitudeOutOfRange_thenViolation() {
        StoreDto dto = new StoreDto("Store", 52.0, 200.0, 10.0);
        Set<ConstraintViolation<StoreDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("longitude");
    }
} 