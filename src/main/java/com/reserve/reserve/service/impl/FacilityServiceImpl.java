package com.reserve.reserve.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.reserve.reserve.dto.CreateFacility;
import com.reserve.reserve.dto.ModifyFacility;
import com.reserve.reserve.dto.response.FacilityFileDto;
import com.reserve.reserve.dto.response.FacilityResponseDto;
import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.FacilityImage;
import com.reserve.reserve.exception.ApiException;
import com.reserve.reserve.repository.CourtRepository;
import com.reserve.reserve.repository.FacilityImageRepository;
import com.reserve.reserve.repository.FacilityRepository;
import com.reserve.reserve.service.FacilityService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService{

    private final FacilityRepository facilityRepository;
    private final FacilityImageRepository facilityImageRepository;
    private final CourtRepository courtRepository;

    @Value("${config.upload-file-path}")
    private String baseFilepath;

    @Override
    @Transactional
    public void modifyFacility(Long facilityId, ModifyFacility modifyFacility) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new ApiException("404", "시설 데이터가 존재하지 않습니다."));
        facility.modifyFacility(modifyFacility.getAdress(), modifyFacility.getName(), modifyFacility.getContent());

        List<FacilityImage> facilityImages = facilityImageRepository.findByFacility(facility);

        if(modifyFacility.getFiles() != null){
            FacilityFileDto facilityFileDto = new FacilityFileDto();

            facilityFileDto.deleteFacilityImage(FacilityFileDto.toFacilityFileDto(facilityImages), baseFilepath);
            facilityImageRepository.deleteByFacilityIdx(facilityId);
            
            List<FacilityFileDto> fileDtos = facilityFileDto.uploadFacilityFile(modifyFacility.getFiles(), baseFilepath);
            List<FacilityImage> images = facilityFileDto.setFacilitImages(fileDtos, facility);
            
            images.forEach(facilityImage -> {
                facilityImageRepository.save(facilityImage);
            });
        }
    }

    @Override
    public FacilityResponseDto getFacility(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new ApiException("404", "데이터가 존재하지 않습니다."));
        List<FacilityImage> facilityImage = facilityImageRepository.findByFacility(facility);
        List<Court> court = courtRepository.findByFacility(facility);

        return FacilityResponseDto.toFacilityResponseDto(facility, facilityImage, court);
    }

    @Override
    @Transactional
    public void registerFacility(CreateFacility facility) {

        Facility paramFacility = new Facility(facility.getAdress(), facility.getName());

        Facility regFacility = facilityRepository.save(paramFacility);

        if(regFacility.getIdx() == null) throw new ApiException("404", "시설 등록 중 에러가 발생하였습니다.");
        
        if (facility.getFiles() != null) {
            FacilityFileDto facilityFileDto = new FacilityFileDto();

            List<FacilityFileDto> facilityImages = facilityFileDto.uploadFacilityFile(facility.getFiles(), baseFilepath);
            
            List<FacilityImage> images = facilityFileDto.setFacilitImages(facilityImages, regFacility);
            
            images.forEach(facilityImage -> {
                facilityImageRepository.save(facilityImage);
            });
        }
    }

}
