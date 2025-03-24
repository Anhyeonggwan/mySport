package com.reserve.reserve.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "MATCH_TBL")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private MatchStatus status;

    @Column(name = "MANAGER", nullable = false)
    private String manager;

    @Column(name = "TEAM_CAPACITY", nullable = false)
    private int teamCapacity;

    @Column(name = "MATCH_DATE", nullable = false)
    private LocalDate matchDate;

    @Column(name = "MATCH_TIME", nullable = false)
    private int matchTime;

    @Column(name = "MATCH_PRICE", nullable = false)
    private int matchPrice;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 지연로딩, eager 즉시 로딩 toOne은 지연로딩 사용
    @JoinColumn(name = "court_id")
    private Court court;

    public void changeDeadLineStatus(){
        this.status = MatchStatus.CLOSE_TO_DEADLINE;
    }

    public void changeFinishStatus(){
        this.status = MatchStatus.FINISH;
    }

    public void changeApplicableStatus(){
        this.status = MatchStatus.APPLICABLE;
    }

}
