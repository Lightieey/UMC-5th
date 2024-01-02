package com.umc.demo.web.dto;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class ReveiwDTO{
        @NotBlank
        String title;
        @NotNull
        Float score;
        @NotBlank
        String body;
    }

    @Getter
    public static class MissionDTO{
        @NotNull
        Integer reward;
        @FutureOrPresent
        LocalDate deadline;
        @NotBlank
        String missionSpec;
    }
}
