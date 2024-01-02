package com.umc.demo.service.StoreService;

import com.umc.demo.converter.StoreConverter;
import com.umc.demo.domain.Mission;
import com.umc.demo.domain.Review;
import com.umc.demo.repository.MemberRepository;
import com.umc.demo.repository.MissionRepository;
import com.umc.demo.repository.ReviewRepository;
import com.umc.demo.repository.StoreRepository;
import com.umc.demo.web.dto.StoreRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService{

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    @Override
    public Review createReview(Long memberId, Long storeId, StoreRequestDTO.ReveiwDTO request) {

        Review review = StoreConverter.toReview(request);

        review.setMember(memberRepository.findById(memberId).get());
        review.setStore(storeRepository.findById(storeId).get());

        return reviewRepository.save(review);
    }

    @Override
    public Mission createMission(Long storeId, StoreRequestDTO.MissionDTO request) {

        Mission mission = StoreConverter.toMission(request);

        mission.setStore(storeRepository.findById(storeId).get());

        return missionRepository.save(mission);
    }
}
