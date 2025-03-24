package com.reserve.reserve.service.impl;

import org.springframework.stereotype.Service;

import com.reserve.reserve.dto.CreateReservation;
import com.reserve.reserve.entity.Court;
import com.reserve.reserve.exception.ApiException;
import com.reserve.reserve.repository.CourtRepository;
import com.reserve.reserve.service.ReserveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService{

    private final CourtRepository courtRepository;

    @Override
    public void registReserve(CreateReservation createReservation) {
        Court court = courtRepository.findById(createReservation.getCourtId())
        .orElseThrow(() -> new ApiException("404", "구장 정보가 존재하지 않습니다."));
    
        
    }
    

}
