package com.reserve.reserve.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reserve.reserve.dto.MatchListRequest;
import com.reserve.reserve.dto.response.MatchPageResponseDto;
import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;
import com.reserve.reserve.entity.Match;

public interface MatchCustomRepositury {

    List<MatchResponseDto> getSearchMatch(MatchSearchDto searchDto);

    Page<MatchPageResponseDto> getMatches(MatchListRequest request, Pageable pageable);

}
