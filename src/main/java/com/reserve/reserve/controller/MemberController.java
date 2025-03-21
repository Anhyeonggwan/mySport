package com.reserve.reserve.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserve.reserve.dto.createMember;
import com.reserve.reserve.entity.Member;
import com.reserve.reserve.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository repository;

    @PostMapping("/save")
    public void memberSave(@RequestBody createMember memberDto) {
        Member member = Member.builder()
        .userId(memberDto.getUserId())
        .userName(memberDto.getUserName())
        .email(memberDto.getEmail())
        .phone(memberDto.getPhone())
        .build();

        repository.save(member);
    }
    

}
