package com.reserve.reserve;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reserve.reserve.config.JPAConfig;
import com.reserve.reserve.dto.CancelPlayer;
import com.reserve.reserve.dto.CreateMatch;
import com.reserve.reserve.dto.CreateMatchPlayer;
import com.reserve.reserve.dto.response.MatchDetailDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.MatchPlayer;
import com.reserve.reserve.entity.MatchStatus;
import com.reserve.reserve.entity.Member;
import com.reserve.reserve.entity.QMatch;
import com.reserve.reserve.exception.ApiException;
import com.reserve.reserve.repository.MatchCustomRepositury;
import com.reserve.reserve.repository.MatchPlayerRepository;
import com.reserve.reserve.repository.MatchRepository;
import com.reserve.reserve.repository.MemberRepository;
import com.reserve.reserve.service.MatchService;
import com.reserve.reserve.util.Utils;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
//@Log4j2
@Import(JPAConfig.class)
public class MatchTest {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchCustomRepositury matchCustomRepositury;

    @Autowired
    private MatchPlayerRepository matchPlayerRepository;

    @Autowired
    EntityManager manager;

    @AfterEach
    void afterEach(){

    }

    @Test
    @DisplayName("매치 상세 조회 테스트")
    void getMatchDetail(){
        /* when */
        MatchDetailDto detailDto = matchService.getMatchDetail(14L);

        /* then */
        Assertions.assertThat(detailDto.getMatchPrice())
        .isEqualTo(11000);
    }

    @Test
    @DisplayName("@Query 테스트")
    void selectQeuryTest(){
        // /* when */
        // MatchPlayer matchPlayer = matchPlayerRepository.selectMatchPlayer(3L, "bombom841");

        // /* then */
        // Assertions.assertThat(matchPlayer.getIdx()).isEqualTo(6L);
    }

    @Test
    @DisplayName("환불 정책 테스트")
    void calcReturnPolicy(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime matchDay = LocalDateTime.of(2025, 3, 21, 22, 0, 0);

        int diffHour = (int) ChronoUnit.HOURS.between(today, matchDay);
        int diffMinute = (int) ChronoUnit.MINUTES.between(today, matchDay);

        Assertions.assertThat(diffHour).isEqualTo(22);
        Assertions.assertThat(diffMinute).isEqualTo(1363);
    }

    @Test
    @DisplayName("매치 플레이어가 매치 취소")
    void cancelMatch(){
        /* given */
        CancelPlayer createMatchPlayer = CancelPlayer.builder()
        .matchIdx(14L)
        .memberId("bombom841")
        .build();

        /* when */
        matchService.cancelPlayer(createMatchPlayer);
    }

    @Test
    @DisplayName("매치 플레이어 등록")
    void matchPlayerSave(){
        /* given */
        CreateMatchPlayer createMatchPlayer = CreateMatchPlayer.builder()
        .matchId(3L)
        .memberId("bombom841")
        .build();

        /* when */
        matchService.saveMatchPlayer(createMatchPlayer);
    }

    @Test
    @DisplayName("매치 플레이어 카운트")
    void matchPlayerCount(){
        /* when */
        int playerCnt = matchPlayerRepository.countByMatchIdx(14L);

        /* then */
        Assertions.assertThat(playerCnt).isEqualTo(4);
    }

    @Test
    @DisplayName("매치 플레이어 등록 예외 처리")
    void matchPlayerSaveException(){
        /* given */
        
        CreateMatchPlayer createMatchPlayer = CreateMatchPlayer.builder()
        .matchId(3L)
        .memberId("bombom84")
        .build();

        /* when */
        assertThrows(ApiException.class, () -> matchService.saveMatchPlayer(createMatchPlayer));

    }

    @Test
    @DisplayName("Querydsl where 테스트")
    void querydslWhereTest(){
        /* given */
        MatchSearchDto searchDto = MatchSearchDto.builder()
        .match_time(22)
        .build();

        /* when */
        List<MatchResponseDto> matches = matchCustomRepositury.getSearchMatch(searchDto);

        /* then */
        Assertions.assertThat(matches.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Querydsl 테스트")
    void querydslTest(){
        Match match = Match.builder()
        //.matchType("0002")
        .status(MatchStatus.APPLICABLE)
        .manager("bombom843")
        .teamCapacity(18)
        .matchDate(LocalDate.now())
        .matchTime(10)
        .matchPrice(12000)
        .build();
        manager.persist(match);

        JPAQueryFactory factory = new JPAQueryFactory(manager);
        QMatch qMatch = QMatch.match;

        Match result = factory
        .select(qMatch)
        .from(qMatch)
        .fetchFirst();

        Assertions.assertThat(result.getIdx()).isEqualTo(3L);
    }

    @Test
    @DisplayName("경기 전체 조회")
    void getAllMatch(){
        /* when */
        List<MatchResponseDto> matches = matchService.getMatches();
        
        /* then */
        Assertions.assertThat(matches.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("경기 등록 테스트")
    void registMatchTest(){
        /* given */
        CreateMatch createMatch = CreateMatch.builder()
        .status(MatchStatus.APPLICABLE)
        .manager("bombom842")
        .teamCapacity(18)
        .matchDate(LocalDate.now())
        .matchTime(18)
        .matchPrice(11000)
        .courtId(7L)
        .build();

        /* when */
        matchService.matchSave(createMatch);
    }

    // @Test
    // @DisplayName("경기 등록 테스트")
    // void matchSaveTest(){
    //     CreateMatch createMatch = CreateMatch.builder()
    //     .matchType("0001") // 축구
    //     .status("0001") // 신청 진행중
    //     .manager("bombom846")
    //     .teamCapacity(18) // 6 vs 6 3파전
    //     .matchDate(LocalDate.now())
    //     .matchTime(18) // 18시
    //     .matchPrice(11000) // 매치 참가비
    //     .facilityIdx(4L)
    //     .build();

    //     matchService.matchSave(createMatch);
    // }

}
