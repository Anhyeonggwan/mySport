package com.reserve.reserve.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.entity.QMatch;
import com.reserve.reserve.repository.MatchCustomRepositury;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MatchCustomRepositoryImpl implements MatchCustomRepositury{

    private final JPAQueryFactory query;

    @Override
    public List<MatchResponseDto> getSearchMatch(MatchSearchDto searchDto) {

        QMatch qMatch = QMatch.match;

        List<MatchResponseDto> matches = query
            .select(
                Projections.fields(
                    MatchResponseDto.class,
                    qMatch.idx.as("idx")
                    //, qMatch.matchType
                    , qMatch.status
                    , qMatch.manager
                    , qMatch.teamCapacity
                    , qMatch.matchDate
                    , qMatch.matchTime
                    , qMatch.matchPrice
                )
            )
            .from(qMatch)
            .where(qMatch.matchTime.eq(searchDto.getMatch_time()))
            .fetch();
        
        return matches;
    }
    
}
