package com.reserve.reserve.repository.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reserve.reserve.dto.MatchListRequest;
import com.reserve.reserve.dto.response.MatchPageResponseDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.entity.MatchStatus;
import com.reserve.reserve.entity.QMatch;
import com.reserve.reserve.entity.QMatchPlayer;
import com.reserve.reserve.entity.SportType;
import com.reserve.reserve.repository.MatchCustomRepositury;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MatchCustomRepositoryImpl implements MatchCustomRepositury{

    private final JPAQueryFactory query;

    private QMatch match = QMatch.match;

    @Override
    public Page<MatchPageResponseDto> getMatches(MatchListRequest request, Pageable pageable) {
        
        QMatchPlayer matchPlayer = QMatchPlayer.matchPlayer;

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
                , matchPlayer.count().as("playerCnt")
            )
        ).from(match)
        .leftJoin(match.court)
        .leftJoin(matchPlayer).on(matchPlayer.match.eq(match))
        .groupBy(match.idx)
        .where(
            statusEq(request.getMatchStatus())
            , sportTypeEq(request.getSportType())
            , teamCapacityEq(request.getTeamCapacity())
            , betweentDate(request.getStartDate(), request.getEndDate())
        )
        .orderBy(match.idx.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

        JPAQuery<Long> countQuery = query
        .select(match.count())
        .from(match);

        return PageableExecutionUtils.getPage(matches, pageable, () -> countQuery.fetchOne());
    }

    private BooleanExpression betweentDate(LocalDate starDate, LocalDate endDate){
        return (starDate != null && endDate != null) ? match.matchDate.between(starDate, endDate) : null;
    }

    private BooleanExpression teamCapacityEq(int teamCapacity){
        return (teamCapacity > 0) ? match.teamCapacity.eq(teamCapacity) : null;
    }

    private BooleanExpression sportTypeEq(SportType sportType){ // 매치 종목
        return (sportType != null) ? match.court.sportType.eq(sportType) : null;
    }

    private BooleanExpression statusEq(MatchStatus matchStatus){ // 매치 상태
        return (matchStatus != null) ? match.status.eq(matchStatus) : null;
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
