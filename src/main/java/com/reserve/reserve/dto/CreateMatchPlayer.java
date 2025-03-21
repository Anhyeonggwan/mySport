package com.reserve.reserve.dto;

import java.time.LocalDateTime;

import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.MatchPlayer;
import com.reserve.reserve.entity.Member;
import com.reserve.reserve.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateMatchPlayer {

    private String memberId;
    private Long matchId;

    public MatchPlayer toEntity(Member member, Match match){
        
        MatchPlayer player = MatchPlayer.builder()
        .regDate(Utils.dateFormat(LocalDateTime.now()))
        .match(match)
        .member(member)
        .build();

        return player;
    }

}
