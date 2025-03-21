package com.reserve.reserve.dto.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.reserve.reserve.dto.CreateFacility;
import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.FacilityImage;
import com.reserve.reserve.exception.ApiException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FacilityFileDto {

    private static final int MAX_FILE_CNT = 3;

    private String filename;
    private String filepath;
    private boolean isMain;

    public static List<FacilityFileDto> toFacilityFileDto(List<FacilityImage> facilityImages){
        List<FacilityFileDto> facilityFileDtos = facilityImages.stream()
        .map(FacilityFileDto::toFacilityFileDto).toList();
        return facilityFileDtos;
    }

    private static FacilityFileDto toFacilityFileDto(FacilityImage facilityImage){
        FacilityFileDto fileDto = FacilityFileDto.builder()
        .filename(facilityImage.getFilename())
        .filepath(facilityImage.getFilepath())
        .build();
        return fileDto;
    }

    public void deleteFacilityImage(List<FacilityFileDto> facilityImages, String basePath){
        facilityImages.forEach(facilityImage -> {
            Resource resource = new FileSystemResource(facilityImage.getFilepath());

            try {
                resource.getFile().delete();
                String contentType = Files.probeContentType(resource.getFile().toPath());

                if(contentType.startsWith("image")){
                    String fileParent = resource.getFile().getParent();
                    String fileName = resource.getFilename();

                    File thumbnailFile = new File(fileParent + "/thumbnail" + fileName);

                    thumbnailFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiException("404", "파일 처리 중 에러가 발생하였습니다.");
            }
        });
    }

    public List<FacilityImage> setFacilitImages(List<FacilityFileDto> facilityImages, Facility facility){
        
        List<FacilityImage> images = facilityImages.stream()
        .map(image -> FacilityFileDto.setFacilitImage(image, facility)).toList();

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

    public List<FacilityFileDto> uploadFacilityFile(List<MultipartFile> files, String baseFilepath){

        if(files.size() > MAX_FILE_CNT) throw new ApiException("404", "최대 파일 개수를 초과하였습니다.");
        List<FacilityFileDto> fileDtos = new ArrayList<>();

        for(int i = 0; i < files.size(); i++){
            MultipartFile file = files.get(i);
            boolean mainFile = true;
            if(i > 0) mainFile = false;

            String originalName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String folderPath = makeFolder(baseFilepath);

            Path savePath = Paths.get(folderPath, uuid + "_" + originalName);

            try {
                file.transferTo(savePath);
                
                if(Files.probeContentType(savePath).startsWith("image")){ // 이미지 파일일 경우
                    makeThumbnailFolder(folderPath);
                    File thumbnailFile = new File(folderPath + "/thumbnail", "s_" + uuid + "_" + originalName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);
                }

                FacilityFileDto fileDto = new FacilityFileDto(originalName, savePath.toString(), mainFile);
                fileDtos.add(fileDto);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiException("404", "파일업로드 중 에러가 발생하였습니다.");
            }
        }

        return fileDtos;
    }

    private void makeThumbnailFolder(String basePath){
        File thumbnailFolder = new File(basePath + "/thumbnail");

        if(thumbnailFolder.exists() == false){
            thumbnailFolder.mkdir();
        }
    }

    // 일별 폴더 생성
    private String makeFolder(String baseFilepath){
        String dateFolderName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        File dateFolder = new File(baseFilepath, dateFolderName);

        if(dateFolder.exists() == false){
            dateFolder.mkdir();
        }

        return baseFilepath + "/" + dateFolderName;
    }

}
