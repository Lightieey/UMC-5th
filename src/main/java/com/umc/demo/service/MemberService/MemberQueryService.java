package com.umc.demo.service.MemberService;

import com.umc.demo.domain.Member;
import com.umc.demo.domain.Review;
import com.umc.demo.domain.mapping.MemberMission;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MemberQueryService {

    Optional<Member> findMember(Long id);
    Optional<MemberMission> findMemberMission(Long id);
    Page<Review> getReviewList(Long memberId, Integer page);
    Page<MemberMission> getMissionList(Long memberId, String status, Integer page);
}
