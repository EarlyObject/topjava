package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.util.Set;

public  class JdbcValidator {
    private static final Logger log = LoggerFactory.getLogger(JdbcValidator.class);
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = validatorFactory.getValidator();

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        for (ConstraintViolation<T> violation : violations) {
            log.info(violation.getMessage());
        }
        if (!violations.isEmpty()) {
            violations.forEach(n->log.info(n.getMessage()));

            throw new ConstraintViolationException(violations);
        }
    }
}
