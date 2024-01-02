package com.umc.demo.service.StoreService;

import com.umc.demo.domain.Mission;
import com.umc.demo.domain.Review;
import com.umc.demo.domain.Store;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface StoreQueryService {
    Optional<Store> findStore(Long id);
    Optional<Mission> findMission(Long id);
    Page<Review> getReviewList(Long StoreId, Integer page);
}
