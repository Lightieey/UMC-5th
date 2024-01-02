package com.umc.demo.repository;

import com.umc.demo.domain.Member;
import com.umc.demo.domain.Review;
import com.umc.demo.domain.enums.MemberStatus;
import com.umc.demo.domain.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    Page<MemberMission> findAllByMemberAndStatus(Member member, MemberStatus status, PageRequest pageRequest);
}
