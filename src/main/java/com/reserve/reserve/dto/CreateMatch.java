package com.reserve.reserve.dto;

import java.time.LocalDate;

import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.MatchStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMatch {

    //private SportType matchType;
    private MatchStatus status;
    private String manager;
    private int teamCapacity;
    private LocalDate matchDate;
    private int matchTime;
    private int matchPrice;
    private Long courtId;

    public Match toEntity(Court court){
        
        Match match = Match.builder()
        //.matchType(this.matchType)
        .status(this.status)
        .manager(this.manager)
        .teamCapacity(this.teamCapacity)
        .matchDate(this.matchDate)
        .matchTime(this.matchTime)
        .matchPrice(this.matchPrice)
        .court(court)
        .build();
        
        return match;
    }

}
