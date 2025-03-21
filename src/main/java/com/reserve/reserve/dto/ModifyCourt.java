package com.reserve.reserve.dto;

import com.reserve.reserve.entity.SportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ModifyCourt {

    private String name;
    private SportType sportType;
    private int capacity;
    private String description;
    private boolean indoor;
    private boolean active;

}
