package com.umc.demo.web.controller;

import com.umc.demo.apiPayload.ApiResponse;
import com.umc.demo.converter.MemberConverter;
import com.umc.demo.converter.StoreConverter;
import com.umc.demo.domain.Member;
import com.umc.demo.domain.mapping.MemberMission;
import com.umc.demo.service.MemberService.MemberCommandService;
import com.umc.demo.service.MemberService.MemberQueryService;
import com.umc.demo.validation.annotation.CheckMissionStatus;
import com.umc.demo.validation.annotation.CheckPage;
import com.umc.demo.validation.annotation.ExistMember;
import com.umc.demo.validation.annotation.ExistMission;
import com.umc.demo.validation.annotation.ExistStore;
import com.umc.demo.validation.annotation.NotExistMemberMission;
import com.umc.demo.web.dto.MemberRequestDTO;
import com.umc.demo.web.dto.MemberResponseDTO;
import com.umc.demo.web.dto.StoreResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/missions/{missionId}")
    public ApiResponse<MemberResponseDTO.CreateMemberMissionResultDTO> createMemberMission(@ExistMission @NotExistMemberMission @PathVariable(name = "missionId") Long missionId,
                                                                                           @RequestParam(name = "memberId") Long memberId) {
        MemberMission memberMission = memberCommandService.createMemberMission(memberId, missionId);
        return ApiResponse.onSuccess(MemberConverter.toCreateMemberMissionResultDTO(memberMission));
    }

    @GetMapping("/reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API",description = "내가 작성한 리뷰 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자 id를 보내주세요"),
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 page 번호를 전달해주세요"),
    })
    public ApiResponse<MemberResponseDTO.ReviewPreViewListDTO> getReviewList(@CheckPage @RequestParam(name = "page") Integer page,
                                                                             @ExistMember @RequestParam(name = "memberId") Long memberId){
        return ApiResponse.onSuccess(MemberConverter.toReviewPreViewListDTO(memberQueryService.getReviewList(memberId, page - 1)));
    }

    @GetMapping("/missions")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API",description = "내가 작성한 리뷰 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자 id를 보내주세요"),
            @Parameter(name = "status", description = "진행 중인 미션은 CHALLENGING, 진행 완료 미션은 COMPLETE를 보내주세요"),
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 page 번호를 전달해주세요"),
    })
    public ApiResponse<MemberResponseDTO.MissionListDTO> getMissionList(@CheckPage @RequestParam(name = "page") Integer page,
                                                                             @CheckMissionStatus @RequestParam(name = "status") String status,
                                                                             @ExistMember @RequestParam(name = "memberId") Long memberId){
        return ApiResponse.onSuccess(MemberConverter.toMissionListDTO(memberQueryService.getMissionList(memberId, status, page - 1)));
    }
}
