package com.umc.demo.service.MemberService;

import com.umc.demo.apiPayload.code.status.ErrorStatus;
import com.umc.demo.apiPayload.exception.handler.FoodCategoryHandler;
import com.umc.demo.converter.MemberConverter;
import com.umc.demo.converter.MemberPreferConverter;
import com.umc.demo.domain.FoodCategory;
import com.umc.demo.domain.Member;
import com.umc.demo.domain.enums.MissionStatus;
import com.umc.demo.domain.mapping.MemberMission;
import com.umc.demo.domain.mapping.MemberPrefer;
import com.umc.demo.repository.FoodCategoryRepository;
import com.umc.demo.repository.MemberMissionRepository;
import com.umc.demo.repository.MemberRepository;
import com.umc.demo.repository.MissionRepository;
import com.umc.demo.repository.StoreRepository;
import com.umc.demo.web.dto.MemberRequestDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request) {

        Member newMember = MemberConverter.toMember(request);
        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> {
                    return foodCategoryRepository.findById(category).orElseThrow(() -> new FoodCategoryHandler(
                            ErrorStatus.FOOD_CATEGORY_NOT_FOUND));
                }).collect(Collectors.toList());

        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);

        memberPreferList.forEach(memberPrefer -> {memberPrefer.setMember(newMember);});

        return memberRepository.save(newMember);
    }

    @Override
    @Transactional
    public MemberMission createMemberMission(Long memberId, Long missionId) {

        MemberMission memberMission = MemberMission.builder()
                .status(MissionStatus.CHALLENGING)
                .build();

        memberMission.setMember(memberRepository.findById(memberId).get());
        memberMission.setMission(missionRepository.findById(missionId).get());

        return memberMissionRepository.save(memberMission);
    }
}
