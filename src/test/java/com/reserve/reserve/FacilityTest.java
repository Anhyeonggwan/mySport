package com.reserve.reserve;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.FacilityImage;
import com.reserve.reserve.entity.TestClass;
import com.reserve.reserve.repository.FacilityRepository;
import com.reserve.reserve.repository.TestRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class FacilityTest {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private TestRepository testRepository;

    @Test
    @DisplayName("시설 정보 수정 테스트")
    void modifyTest(){
        Facility facility = facilityRepository.findById(4L).orElseThrow();
        facility.modifyFacility("한라동백아파트 풋살B구장", "삼동로 34", null);
    }

    @Test
    @DisplayName("시설 등록 테스트")
    void test(){
        TestClass testClass = TestClass.builder()
        .name("name").build();

        TestClass test = testRepository.save(testClass);
        Assertions.assertThat(test.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("시설 등록 테스트")
    void facilitySaveTest(){
        /* given */
        Facility facilityEntity = Facility.builder()
        .adress("한라동백아파트")
        .name("한라아파트 풋살 A구장")
        .build();

        /* when */
        Facility facility = facilityRepository.save(facilityEntity);

        Assertions.assertThat(facility.getName()).isEqualTo("한라아파트 풋살 A구장");
    }

    private List<FacilityImage> getFacilityImages(){
        List<FacilityImage> facilityImages = new ArrayList<>();
        

        return facilityImages;
    }

}
