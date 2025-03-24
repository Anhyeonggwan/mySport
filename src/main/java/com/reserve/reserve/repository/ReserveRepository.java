package com.reserve.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserve.reserve.entity.CourtReserve;

public interface ReserveRepository extends JpaRepository<CourtReserve, Long>{

}
