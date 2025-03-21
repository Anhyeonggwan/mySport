package com.reserve.reserve.service.impl;

import org.springframework.stereotype.Service;

import com.reserve.reserve.dto.CreateCourt;
import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.exception.ApiException;
import com.reserve.reserve.repository.CourtRepository;
import com.reserve.reserve.repository.FacilityRepository;
import com.reserve.reserve.service.CourtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService{

    private final CourtRepository courtRepository;
    private final FacilityRepository facilityRepository;

    @Override
    @Transactional
    public void registCourt(CreateCourt createCourt) {
        Facility facility = facilityRepository.findById(createCourt.getFacilityId())
        .orElseThrow(() -> new ApiException("404", "시설 데이터가 존재하지 않습니다."));
        Court courtEntity = createCourt.toEntity(createCourt, facility);
        courtRepository.save(courtEntity);
    }
}
