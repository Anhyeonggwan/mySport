package com.reserve.reserve.dto.response;

import java.time.LocalDate;

import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.MatchStatus;
import com.reserve.reserve.entity.SportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter // DTO 변환 시 getter 필수 
// 미 설정 시 com.fasterxml.jackson.databind.exc.InvalidDefinitionException 발생 
//인스턴스 변수가 public이어야 Jackson 라이브러리가 접근할 수 있는데 변수가 private 이어서 접근할 수가 없어서 접근할 수 있도록 getter 설정
public class MatchResponseDto {
    private Long idx;
    private SportType matchType;
    private MatchStatus status;
    private String manager;
    private int teamCapacity;
    private LocalDate matchDate;
    private int matchTime;
    private int matchPrice;

    public static MatchResponseDto toMatchResponseDto(Match match){

        MatchResponseDto dto = MatchResponseDto.builder()
        .idx(match.getIdx())
        //.matchType(match.getMatchType())
        .status(match.getStatus())
        .manager(match.getManager())
        .teamCapacity(match.getTeamCapacity())
        .matchDate(match.getMatchDate())
        .matchTime(match.getMatchTime())
        .matchPrice(match.getMatchPrice())
        .build();

        return dto;
    }
}
