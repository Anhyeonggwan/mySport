package com.reserve.reserve.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class CreateReservation {

    private String reservationName;
    private LocalDate reserveDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long courtId;

}
