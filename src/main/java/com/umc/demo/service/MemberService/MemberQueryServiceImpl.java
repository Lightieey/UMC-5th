package com.umc.demo.service.MemberService;

import com.umc.demo.domain.Member;
import com.umc.demo.domain.Review;
import com.umc.demo.domain.Store;
import com.umc.demo.domain.enums.MemberStatus;
import com.umc.demo.domain.mapping.MemberMission;
import com.umc.demo.repository.MemberMissionRepository;
import com.umc.demo.repository.MemberRepository;
import com.umc.demo.repository.ReviewRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Optional<Member> findMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<MemberMission> findMemberMission(Long id) {
        return memberMissionRepository.findById(id);
    }

    @Override
    public Page<Review> getReviewList(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).get();
        return reviewRepository.findAllByMember(member, PageRequest.of(page, 10));
    }

    @Override
    public Page<MemberMission> getMissionList(Long memberId, String status, Integer page) {
        Member member = memberRepository.findById(memberId).get();
        return memberMissionRepository.findAllByMemberAndStatus(member, MemberStatus.valueOf(status),
                PageRequest.of(page, 10));
    }
}
