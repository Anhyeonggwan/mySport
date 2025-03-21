package com.reserve.reserve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserve.reserve.entity.Court;
import com.reserve.reserve.entity.Facility;

public interface CourtRepository extends JpaRepository<Court, Long>{

    List<Court> findByFacility(Facility facility);

}
