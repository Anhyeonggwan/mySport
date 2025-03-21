package com.reserve.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserve.reserve.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
