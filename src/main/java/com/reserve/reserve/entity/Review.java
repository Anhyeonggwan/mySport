package com.reserve.reserve.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Table(name = "REVIEW_TBL")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;
    
    @Column(name = "UPDATE_DATE", nullable = true)
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 지연로딩, eager 즉시 로딩 toOne은 지연로딩 사용
    @JoinColumn(name = "MATCH_IDX")
    @ToString.Exclude
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 지연로딩, eager 즉시 로딩 toOne은 지연로딩 사용
    @JoinColumn(name = "USER_ID")
    @ToString.Exclude
    private Member member;

}
