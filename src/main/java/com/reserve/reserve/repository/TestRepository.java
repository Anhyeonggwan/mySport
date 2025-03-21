package com.reserve.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserve.reserve.entity.TestClass;

public interface TestRepository extends JpaRepository<TestClass, Long>{

}
