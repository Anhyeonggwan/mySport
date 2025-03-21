package com.reserve.reserve.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.reserve.reserve.dto.response.FacilityFileDto;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.FacilityImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateFacility {

    private String adress;
    private String name;

    private List<MultipartFile> files;

    public List<FacilityImage> setFacilitImages(List<FacilityFileDto> facilityImages, Facility facility){
        
        List<FacilityImage> images = facilityImages.stream()
        .map(image -> CreateFacility.setFacilitImage(image, facility)).toList();

        return images;
    }

    private static FacilityImage setFacilitImage(FacilityFileDto fileDto, Facility facility){
        return FacilityImage.builder()
        .filename(fileDto.getFilename())
        .filepath(fileDto.getFilepath())
        .isMain(fileDto.isMain())
        .facility(facility)
        .build();
    }

}