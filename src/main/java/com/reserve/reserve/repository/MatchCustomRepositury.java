package com.reserve.reserve.repository;

import java.util.List;

import com.reserve.reserve.dto.response.MatchResponseDto;
import com.reserve.reserve.dto.search.MatchSearchDto;

public interface MatchCustomRepositury {

    List<MatchResponseDto> getSearchMatch(MatchSearchDto searchDto);

}
