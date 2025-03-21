package com.reserve.reserve.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reserve.reserve.dto.CancelPlayer;
import com.reserve.reserve.dto.CreateMatch;
import com.reserve.reserve.dto.CreateMatchPlayer;
import com.reserve.reserve.dto.response.MatchDetailDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.service.MatchService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchDetailDto> getMatchDetail(@PathVariable(name = "matchId") Long matchId) {
        return ResponseEntity.ok(matchService.getMatchDetail(matchId));
    }
    

    @DeleteMapping("/cancelPlayer")
    public void cancelPlayer(@RequestBody CancelPlayer playerDto){
        matchService.cancelPlayer(playerDto);
    }

    @PostMapping("/playerSave")
    public void saveMatchPlayer(@RequestBody CreateMatchPlayer playerDto) {
        matchService.saveMatchPlayer(playerDto);
    }
    

    @GetMapping("/searchMatch")
    public ResponseEntity<List<MatchResponseDto>> getMatchSearch(@RequestBody MatchSearchDto searchDto) {
        List<MatchResponseDto> matches = matchService.getMatchSearch(searchDto); 
        return ResponseEntity.ok(matches);
    }
    

    @PostMapping("/save")
    public void postMethodName(@RequestBody CreateMatch entity) {
        matchService.matchSave(entity);
    }

    @GetMapping("/allMatches")
    public ResponseEntity<List<MatchResponseDto>> getMatches() {
        List<MatchResponseDto> matches = matchService.getMatches();
        return ResponseEntity.ok(matches);
    }
    

}
