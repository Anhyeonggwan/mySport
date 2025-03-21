package com.reserve.reserve.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyFacility {

    private String adress;
    private String name;
    private String content;

    private List<MultipartFile> files;

}
