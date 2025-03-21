package com.reserve.reserve.dto;

import java.time.LocalDateTime;

import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.SportType;
import com.reserve.reserve.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateCourt {

    private String name;
    private SportType sportType;
    private String description;
    private int capacity;
    private boolean indoor;
    private boolean active;
    private Long facilityId;

    public Court toEntity(CreateCourt createCourt, Facility facility){
        Court court = Court.builder()
        .name(createCourt.getName())
        .sportType(createCourt.getSportType())
        .description(createCourt.getDescription())
        .capacity(createCourt.getCapacity())
        .indoor(createCourt.isIndoor())
        .active(createCourt.isActive())
        .facility(facility)
        .createAt(Utils.dateFormat(LocalDateTime.now()))
        .build();
        return court;
    }

}
