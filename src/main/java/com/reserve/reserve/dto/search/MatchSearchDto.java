package com.reserve.reserve.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MatchSearchDto {

    private String matchType;
    private String statusType;
    private int match_time;

}
