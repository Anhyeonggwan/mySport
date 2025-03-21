package com.reserve.reserve.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reserve.reserve.dto.MatchListRequest;
import com.reserve.reserve.dto.response.MatchPageResponseDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.QCourt;
import com.reserve.reserve.entity.QMatch;
import com.reserve.reserve.repository.MatchCustomRepositury;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MatchCustomRepositoryImpl implements MatchCustomRepositury{

    private final JPAQueryFactory query;

    @Override
    public Page<MatchPageResponseDto> getMatches(MatchListRequest request, Pageable pageable) {
        
        QMatch match = QMatch.match;
        QCourt court = QCourt.court;

        List<MatchPageResponseDto> matches = query.select(
            Projections.fields(
                MatchPageResponseDto.class
            ,match.idx.as("matchId")
            , match.status.as("status")
            , match.teamCapacity.as("teamCapacity")
            , match.matchDate.as("matchDate")
            , match.matchTime.as("matchTime")
            , match.court.name.as("courtName")
            , match.court.sportType.as("sportType")
            )
        ).from(match)
        .leftJoin(match.court)
        .on(match.court.id.eq(court.id))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

        JPAQuery<Long> countQuery = query
        .select(match.count())
        .from(match);

        return PageableExecutionUtils.getPage(matches, pageable, () -> countQuery.fetchOne());
    }

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
