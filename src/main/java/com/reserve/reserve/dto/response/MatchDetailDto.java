package com.reserve.reserve.dto.response;

import java.time.LocalDate;

import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MatchDetailDto {

    private Long matchId;
    private int teamCapacity;
    private int matchPrice;
    private LocalDate matchDate;
    private int matchTime;
    private String managerName;
    private int remainPlayer;
    private CourtDetailDto courtDetailDto;

    public static MatchDetailDto toMatchDetailDto(Match match, int count){
        MatchDetailDto matchDetailDto = MatchDetailDto.builder()
        .matchId(match.getIdx())
        .teamCapacity(match.getTeamCapacity())
        .matchPrice(match.getMatchPrice())
        .matchTime(match.getMatchTime())
        .matchDate(match.getMatchDate())
        .remainPlayer(remainPlayer(match.getTeamCapacity(), count))
        .courtDetailDto(CourtDetailDto.toCourtDetailDto(match.getCourt()))
        .build();
        return matchDetailDto;
    }

    // 마감까지 남은 사람 수
    private static int remainPlayer(int matchPlayer, int count){
        return matchPlayer - count;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    static class CourtDetailDto{
        private Long courtId;
        private String name;
        private String indoor;

        public static CourtDetailDto toCourtDetailDto(Court court){
            String indoor = court.isIndoor() ? "실내" : "야외";
            CourtDetailDto courtDetailDto = CourtDetailDto.builder()
            .courtId(court.getId())
            .name(court.getName())
            .indoor(indoor)
            .build();
            return courtDetailDto;
        }
    }

}
