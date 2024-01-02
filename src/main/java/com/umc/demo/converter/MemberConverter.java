package com.umc.demo.converter;

import com.umc.demo.domain.Member;
import com.umc.demo.domain.Mission;
import com.umc.demo.domain.Review;
import com.umc.demo.domain.enums.Gender;
import com.umc.demo.domain.mapping.MemberMission;
import com.umc.demo.web.dto.MemberRequestDTO;
import com.umc.demo.web.dto.MemberResponseDTO;
import com.umc.demo.web.dto.MemberResponseDTO.MissionDTO;
import com.umc.demo.web.dto.StoreResponseDTO;
import com.umc.demo.web.dto.StoreResponseDTO.ReviewPreViewDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberResponseDTO.CreateMemberMissionResultDTO toCreateMemberMissionResultDTO(MemberMission memberMission){
        return MemberResponseDTO.CreateMemberMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request){

        Gender gender = null;

        switch (request.getGender()){
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            case 3:
                gender = Gender.NONE;
                break;
        }

        return Member.builder()
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .gender(gender)
                .name(request.getName())
                .memberPreferList(new ArrayList<>())
                .build();
    }

    public static MemberResponseDTO.ReviewPreViewDTO toReviewPreViewDTO(Review review){
        return MemberResponseDTO.ReviewPreViewDTO.builder()
                .storeName(review.getMember().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getBody())
                .build();
    }
    public static MemberResponseDTO.ReviewPreViewListDTO toReviewPreViewListDTO(Page<Review> reviewList){
        List<ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(StoreConverter::toReviewPreViewDTO).collect(Collectors.toList());

        return MemberResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }

    public static MemberResponseDTO.MissionDTO toMissionDTO(MemberMission memberMission){
        Mission mission = memberMission.getMission();
        return MemberResponseDTO.MissionDTO.builder()
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .missionSpec(mission.getMissionSpec())
                .storeId(mission.getStore().getId())
                .createdAt(memberMission.getCreatedAt().toLocalDate())
                .build();
    }
    public static MemberResponseDTO.MissionListDTO toMissionListDTO(Page<MemberMission> memberMissionList){
        List<MissionDTO> MissionDTOList = memberMissionList.stream()
                .map(MemberConverter::toMissionDTO).collect(Collectors.toList());

        return MemberResponseDTO.MissionListDTO.builder()
                .isLast(memberMissionList.isLast())
                .isFirst(memberMissionList.isFirst())
                .totalPage(memberMissionList.getTotalPages())
                .totalElements(memberMissionList.getTotalElements())
                .listSize(MissionDTOList.size())
                .missionList(MissionDTOList)
                .build();
    }
}
