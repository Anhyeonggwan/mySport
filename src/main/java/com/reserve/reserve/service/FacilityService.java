package com.reserve.reserve.service;

import com.reserve.reserve.dto.CreateFacility;
import com.reserve.reserve.dto.ModifyFacility;
import com.reserve.reserve.dto.response.FacilityResponseDto;

public interface FacilityService {

    void registerFacility(CreateFacility facility);

    FacilityResponseDto getFacility(Long facilityId);

    void modifyFacility(Long facilityId, ModifyFacility modifyFacility);

}
