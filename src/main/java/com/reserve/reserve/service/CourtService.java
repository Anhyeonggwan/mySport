package com.reserve.reserve.service;

import com.reserve.reserve.dto.CreateCourt;
import com.reserve.reserve.dto.ModifyCourt;

public interface CourtService {

    void registCourt(CreateCourt createCourt);

    void modifyCourt(Long courtId, ModifyCourt entity);

}
