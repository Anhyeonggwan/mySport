package com.reserve.reserve.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserve.reserve.dto.CreateCourt;
import com.reserve.reserve.service.CourtService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/court")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @PostMapping("/registCourt")
    public void registCourt(@RequestBody CreateCourt entity) {
        courtService.registCourt(entity);
    }
    

}
