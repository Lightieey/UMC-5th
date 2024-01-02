package com.umc.demo.service.StoreService;

import com.umc.demo.domain.Mission;
import com.umc.demo.domain.Review;
import com.umc.demo.web.dto.StoreRequestDTO;

public interface StoreCommandService {

    Review createReview(Long memberId, Long storeId, StoreRequestDTO.ReveiwDTO request);
    Mission createMission(Long storeId, StoreRequestDTO.MissionDTO request);
}
