package com.reserve.reserve.dto;

import java.time.LocalDate;

import com.reserve.reserve.entity.SportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter // @ModelAttribute인 경우 필드에 접근하기 위해 setter 필요
public class MatchListRequest {

    private int pageNumber;
    private int pageSize;
    
    private SportType sportType;
    private LocalDate startDate;
    private LocalDate endDate;

}
