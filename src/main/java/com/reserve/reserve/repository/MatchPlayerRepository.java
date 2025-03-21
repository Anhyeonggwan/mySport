package com.reserve.reserve.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.MatchPlayer;
import com.reserve.reserve.entity.Member;

public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long>{

    @Query(value = "select idx, reg_date, match_id, user_id from match_player_tbl where match_id = :matchIdx and user_id = :userId"
    , nativeQuery = true)
    MatchPlayer selectMatchPlayer(@Param("matchIdx") Long matchIdx, @Param("userId") String userId);

    Optional<MatchPlayer> findByMemberAndMatch(Member member, Match match);

    int countByMatchIdx(Long matchId);

}
