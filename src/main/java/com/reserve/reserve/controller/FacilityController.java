package com.reserve.reserve.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserve.reserve.dto.CreateFacility;
import com.reserve.reserve.dto.ModifyFacility;
import com.reserve.reserve.dto.response.FacilityResponseDto;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.repository.FacilityRepository;
import com.reserve.reserve.service.FacilityService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
@Log4j2
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping("/{facilityId}")
    public ResponseEntity<FacilityResponseDto> getFacility(@PathVariable(name = "facilityId") Long facilityId) {
        return ResponseEntity.ok(facilityService.getFacility(facilityId));
    }

    @PostMapping("/modify/{facilityId}")
    public void modifyFacility(@PathVariable(name = "facilityId") Long facilityId, @ModelAttribute("modifyFacility") ModifyFacility modifyFacility) {
        facilityService.modifyFacility(facilityId, modifyFacility);
        
        // Facility facility = repository.findById(facility_idx).orElseThrow();
        // facility.modifyFacility(modifyFacility.getName(), modifyFacility.getAdress(), modifyFacility.getContent());
    }
    
    @PostMapping("/save")
    public void registFacility(@ModelAttribute("entity") CreateFacility entity) {
        facilityService.registerFacility(entity);
    }
    

}
