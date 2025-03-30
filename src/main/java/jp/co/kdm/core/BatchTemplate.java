/**
 * 
 */
package jp.co.kdm.core;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class BatchTemplate<I extends InputEntity, O extends OutputEntity> {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public final O execute(String[] args) {
        log.info("▶️  [START] {}.input()", getClass().getSimpleName());
        I input = input(args);
        validateInput(input);
        log.info("▶️  [START] {}.process()", getClass().getSimpleName());
        O output = process(input);
        log.info("▶️  [START] {}.postProcess()", getClass().getSimpleName());
        postProcess(input, output);
        return output;
    }

    protected abstract I input(String[] args);

    protected abstract O process(I input);

    protected abstract void postProcess(I input, O output);

    private void validateInput(I input) {
        Set<ConstraintViolation<I>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("入力値の検証に失敗しました:\n");
            for (ConstraintViolation<I> violation : violations) {
                sb.append(" - ").append(violation.getPropertyPath())
                  .append(": ").append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
