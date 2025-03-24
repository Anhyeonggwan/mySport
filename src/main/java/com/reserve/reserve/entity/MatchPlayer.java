package com.reserve.reserve.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "MATCH_PLAYER_TBL")
public class MatchPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    
    @Column(name = "REG_DATE", nullable = false)
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 지연로딩, eager 즉시 로딩 toOne은 지연로딩 사용
    @JoinColumn(name = "USER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 지연로딩, eager 즉시 로딩 toOne은 지연로딩 사용
    @JoinColumn(name = "MATCH_ID", referencedColumnName = "idx")
    private Match match;

    public void spendPoint(){
        this.member.spendPoint(match.getMatchPrice());
    }

    public void returnPoint(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime matchDay = LocalDateTime.of(this.match.getMatchDate(), LocalTime.of(this.match.getMatchTime(), 0, 0));

        int diffHour = (int) ChronoUnit.HOURS.between(today, matchDay);
        int diffMinute = (int) ChronoUnit.MINUTES.between(today, matchDay);

        int matchPrice = this.match.getMatchPrice();

        if(diffHour <= ReturnPolicy.TWO_DAY_AGO && diffHour >= ReturnPolicy.ONE_DAY_AGO){
            matchPrice = (int) (matchPrice * ReturnPolicy.RETURN_80);
        }

        if(diffMinute < ReturnPolicy.ONE_DAY_MINUTE_AGO && diffMinute >= ReturnPolicy.ONE_HOUR_HALF_AGO){
            matchPrice = (int) (matchPrice * ReturnPolicy.RETURN_20);
        }else{
            matchPrice = 0;
        }

        this.member.returnPoint(matchPrice);
    }

}
