package com.umc.demo.validation.validator;

import com.umc.demo.apiPayload.code.status.ErrorStatus;
import com.umc.demo.domain.Member;
import com.umc.demo.domain.Mission;
import com.umc.demo.service.MemberService.MemberQueryService;
import com.umc.demo.service.StoreService.StoreQueryService;
import com.umc.demo.validation.annotation.ExistMember;
import com.umc.demo.validation.annotation.ExistMission;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissionExistValidator implements ConstraintValidator<ExistMission, Long> {
    private final StoreQueryService storeQueryService;

    @Override
    public void initialize(ExistMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<Mission> target = storeQueryService.findMission(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
