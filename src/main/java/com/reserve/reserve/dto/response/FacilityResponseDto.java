package com.reserve.reserve.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.FacilityImage;
import com.reserve.reserve.entity.SportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter // DTO 변환 시 getter 필수 
// 미 설정 시 com.fasterxml.jackson.databind.exc.InvalidDefinitionException 발생 
//인스턴스 변수가 public이어야 Jackson 라이브러리가 접근할 수 있는데 변수가 private 이어서 접근할 수가 없어서 접근할 수 있도록 getter 설정
public class FacilityResponseDto {

    private Long id;
    private String adress;
    private String name;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean isNew;
    private List<FacilityImageDto> facilityImage;
    private List<CourtDto> court;

    public static FacilityResponseDto toFacilityResponseDto(Facility facility, List<FacilityImage> facilityImages, List<Court> courts){
        return FacilityResponseDto.builder()
            .id(facility.getIdx())
            .adress(facility.getAdress())
            .name(facility.getName())
            .content(facility.getContent())
            .createAt(facility.getCreateDate())
            .updateAt(facility.getUpdateDate())
            .isNew(facility.isNew())
            .facilityImage(FacilityImageDto.toFacilityImageDtos(facilityImages))
            .court(CourtDto.toCourtDtos(courts))
            .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    static class FacilityImageDto{
        private Long facImageid;
        private String facFilename;
        private String facFilepath;
        private boolean isMain;

        public static List<FacilityImageDto> toFacilityImageDtos(List<FacilityImage> facilityImages){
            List<FacilityImageDto> imageDtos = facilityImages.stream()
            .map(FacilityImageDto::toFacilityImageDto).toList();
            return imageDtos;
        }

        private static FacilityImageDto toFacilityImageDto(FacilityImage facilityImage){
            FacilityImageDto facilityImageDto = FacilityImageDto.builder()
            .facImageid(facilityImage.getId())
            .facFilename(facilityImage.getFilename())
            .facFilepath(facilityImage.getFilepath())
            .isMain(facilityImage.isMain())
            .build();

            return facilityImageDto;
        }
    }
    
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    static class CourtDto{
        private Long courtId;
        private String courtName;
        private SportType sportType;
        private String description;
        private int capacity;
        private boolean indoor;
        private boolean active;

        public static List<CourtDto> toCourtDtos(List<Court> courts){
            List<CourtDto> courtDtos = courts.stream()
            .map(CourtDto::toCourtDto).toList();
            return courtDtos;
        }

        private static CourtDto toCourtDto(Court court){
            return CourtDto.builder()
            .courtId(court.getId())
            .courtName(court.getName())
            .sportType(court.getSportType())
            .description(court.getDescription())
            .capacity(court.getCapacity())
            .indoor(court.isIndoor())
            .active(court.isActive())
            .build();
        }
    }

}