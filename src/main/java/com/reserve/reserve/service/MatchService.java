package com.reserve.reserve.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.reserve.reserve.dto.CancelPlayer;
import com.reserve.reserve.dto.CreateMatch;
import com.reserve.reserve.dto.CreateMatchPlayer;
import com.reserve.reserve.dto.response.MatchDetailDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;

public interface MatchService {

    public void matchSave(CreateMatch createMatch);

    public List<MatchResponseDto> getMatches();

    public List<MatchResponseDto> getMatchSearch(MatchSearchDto searchDto);

    public void saveMatchPlayer(CreateMatchPlayer playerDto);

    public void cancelPlayer(CancelPlayer playerDto);

    public MatchDetailDto getMatchDetail(Long matchId);

}
