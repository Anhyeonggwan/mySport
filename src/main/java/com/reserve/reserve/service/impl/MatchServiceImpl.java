package com.reserve.reserve.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.reserve.reserve.dto.CancelPlayer;
import com.reserve.reserve.dto.CreateMatch;
import com.reserve.reserve.dto.CreateMatchPlayer;
import com.reserve.reserve.dto.MatchListRequest;
import com.reserve.reserve.dto.response.MatchDetailDto;
import com.reserve.reserve.dto.response.MatchPageResponseDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Match;
import com.reserve.reserve.entity.MatchPlayer;
import com.reserve.reserve.entity.MatchStatus;
import com.reserve.reserve.entity.Member;
import com.reserve.reserve.exception.ApiException;
import com.reserve.reserve.repository.CourtRepository;
import com.reserve.reserve.repository.MatchCustomRepositury;
import com.reserve.reserve.repository.MatchPlayerRepository;
import com.reserve.reserve.repository.MatchRepository;
import com.reserve.reserve.repository.MemberRepository;
import com.reserve.reserve.service.MatchService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{

    private static final int CLOSE_TO_DEADLINE_CNT = 5;

    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final MatchCustomRepositury matchCustomRepositury;
    private final MatchPlayerRepository matchPlayerRepository;
    private final CourtRepository courtRepository;

    @Override
    public Page<MatchPageResponseDto> getAllMatches(MatchListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("idx").descending());
        
        Page<MatchPageResponseDto> matchPage = matchCustomRepositury.getMatches(request, pageable);
        
        return matchPage;
    }

    @Override
    public MatchDetailDto getMatchDetail(Long matchId) {
        Match match = matchRepository.findById(matchId)
        .orElseThrow(() -> new ApiException("404", "매치 정보가 존재하지 않습니다."));

        int count = matchPlayerRepository.countByMatchIdx(matchId);

        return MatchDetailDto.toMatchDetailDto(match, count);
    }

    @Override
    public void matchSave(CreateMatch createMatch) {

        memberRepository.findById(createMatch.getManager())
        .orElseThrow(() -> new RuntimeException("회원 정보가 존재하지 않습니다."));

        Court court = courtRepository.findById(createMatch.getCourtId())
        .orElseThrow(() -> new ApiException("404", "코트 정보가 존재하지 않습니다."));

        Match matchEntity = createMatch.toEntity(court);
        matchRepository.save(matchEntity);
    }

    @Override
    public List<MatchResponseDto> getMatches() {
        List<Match> matches = matchRepository.findAll();
        List<MatchResponseDto> matchDtos = matches.stream()
        .map(match -> MatchResponseDto.toMatchResponseDto(match)).toList();

        // List.of() 불변
        // Arrays.asList(null); 가변
        return matchDtos;
    }

    @Override
    public List<MatchResponseDto> getMatchSearch(MatchSearchDto searchDto) {
        return matchCustomRepositury.getSearchMatch(searchDto);
    }

    @Override
    @Transactional
    public void saveMatchPlayer(CreateMatchPlayer playerDto) {

        Member member = memberRepository.findById(playerDto.getMemberId())
        .orElseThrow(() -> new ApiException("500", "회원 데이터가 존재하지 않습니다."));

        Match match = matchRepository.findById(playerDto.getMatchId())
        .orElseThrow(() -> new ApiException("500", "매치 데이터가 존재하지 않습니다."));

        if(match.getStatus() == MatchStatus.FINISH) throw new ApiException("404", "인원이 마감된 매치입니다.");
        if(member.getPoint() < match.getMatchPrice()) throw new ApiException("500", "포인트가 부족합니다.");

        MatchPlayer matchPlayer = playerDto.toEntity(member, match);

        MatchPlayer saveMatchPlayer = matchPlayerRepository.save(matchPlayer);

        if(saveMatchPlayer.getIdx() == null) throw new ApiException("500", "신청 중 오류가 발생하였습니다.");

        int count = matchPlayerRepository.countByMatchIdx(playerDto.getMatchId());

        if(match.getStatus() == MatchStatus.APPLICABLE){
            if(count >= CLOSE_TO_DEADLINE_CNT){
                match.changeDeadLineStatus();
            }
        }

        if(match.getStatus() == MatchStatus.CLOSE_TO_DEADLINE){
            if(count >= match.getTeamCapacity()){
                match.changeFinishStatus();
            }
        }

        saveMatchPlayer.spendPoint();
    }

    @Override
    @Transactional
    public void cancelPlayer(CancelPlayer playerDto) {
        Member member = memberRepository.findById(playerDto.getMemberId())
        .orElseThrow(() -> new ApiException("404", "회원 데이터가 존재하지 않습니다."));

        Match match = matchRepository.findById(playerDto.getMatchIdx())
        .orElseThrow(() -> new ApiException("404", "매치 데이터가 존재하지 않습니다."));

        MatchPlayer matchPlayer = matchPlayerRepository.findByMemberAndMatch(member, match).orElseThrow(() -> new ApiException("404", "플레이어 데이터가 존재하지 않습니다."));
        matchPlayerRepository.deleteById(matchPlayer.getIdx());

        int count = matchPlayerRepository.countByMatchIdx(playerDto.getMatchIdx());

        if(match.getStatus() == MatchStatus.CLOSE_TO_DEADLINE){
            if(count <= CLOSE_TO_DEADLINE_CNT){
                match.changeApplicableStatus();
            }
        }

        if(match.getStatus() == MatchStatus.FINISH){
            if(count <= match.getTeamCapacity()){
                match.changeDeadLineStatus();
            }
        }

        matchPlayer.returnPoint();

    }

}
