package com.reserve.reserve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserve.reserve.entity.Facility;
import com.reserve.reserve.entity.FacilityImage;

public interface FacilityImageRepository extends JpaRepository<FacilityImage, Long>{

    List<FacilityImage> findByFacility(Facility facility);

    List<FacilityImage> findByIsMain(boolean b);

    void deleteByFacility(Facility facility);

    void deleteAllByFacility(Facility facility);

    @Modifying
    @Query(value = "delete from FacilityImage img where img.facility.idx = :idx")
    void deleteByFacilityIdx(@Param("idx") Long idx);

    //List<FacilityImage> findByFacility_Id(Long facilityId);

}
