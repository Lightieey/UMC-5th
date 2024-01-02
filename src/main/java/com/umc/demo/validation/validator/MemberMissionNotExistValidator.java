package com.umc.demo.validation.validator;

import com.umc.demo.apiPayload.code.status.ErrorStatus;
import com.umc.demo.domain.mapping.MemberMission;
import com.umc.demo.service.MemberService.MemberQueryService;
import com.umc.demo.validation.annotation.NotExistMemberMission;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMissionNotExistValidator implements ConstraintValidator<NotExistMemberMission, Long> {

    private final MemberQueryService memberQueryService;

    @Override
    public void initialize(NotExistMemberMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        Optional<MemberMission> target = memberQueryService.findMemberMission(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.STORE_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
