package com.umc.demo.validation.validator;

import com.umc.demo.apiPayload.code.status.ErrorStatus;
import com.umc.demo.domain.enums.MissionStatus;
import com.umc.demo.validation.annotation.CheckMissionStatus;
import com.umc.demo.validation.annotation.CheckPage;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckMissionStatusValidator implements ConstraintValidator<CheckMissionStatus, String> {

    @Override
    public void initialize(CheckMissionStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = value.equals(MissionStatus.CHALLENGING.name()) || value.equals(MissionStatus.COMPLETE.name());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.FOOD_CATEGORY_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;

    }
}
