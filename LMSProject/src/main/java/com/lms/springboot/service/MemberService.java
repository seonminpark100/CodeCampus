package com.lms.springboot.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Spring의 Transactional 사용

import com.lms.springboot.jdbc.IMemberService; // Mybatis Mapper 인터페이스
import com.lms.springboot.jdbc.MemberDTO; // MemberDTO 사용

@Service
public class MemberService { // UserInfoService 대신 MemberService를 사용
    private final IMemberService memberMapper; // Mapper 인터페이스 주입
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(IMemberService memberMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberMapper = memberMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<MemberDTO> getAllUsers() { // UserInfo 대신 MemberDTO 반환
        return memberMapper.select(); // Mybatis를 통해 모든 회원 정보 조회
    }

    @Transactional
    public MemberDTO createUser(MemberDTO memberDTO) { // MemberDTO 사용
        // 기존 사용자 ID 체크 (Mybatis selectOne을 활용)
        MemberDTO existingUser = memberMapper.selectOne(MemberDTO.builder().userId(memberDTO.getUserId()).build());
        if (existingUser != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        // 비밀번호 해싱 (BCrypt)
        String hashedPassword = bCryptPasswordEncoder.encode(memberDTO.getUserPw());
        memberDTO.setUserPw(hashedPassword);

        // 가입일이 설정되지 않았다면 현재 날짜로 설정
        if (memberDTO.getJoinDate() == null) {
            memberDTO.setJoinDate(LocalDate.now());
        }

        // Mybatis Mapper를 통해 DB에 저장
        // insert 메서드가 int를 반환하므로, 저장 성공 여부만 확인하고 DTO 자체를 반환
        int result = memberMapper.insert(memberDTO);
        if (result > 0) {
            return memberDTO; // 성공적으로 저장된 DTO 반환
        } else {
            throw new RuntimeException("사용자 생성에 실패했습니다.");
        }
    }

    // Mybatis 기반의 다른 서비스 메서드 추가 가능 (예: 사용자 업데이트, 삭제, 단일 조회)
    public MemberDTO getUserById(String userId) {
        return memberMapper.selectOne(MemberDTO.builder().userId(userId).build());
    }

    public int updateUser(MemberDTO memberDTO) {
        // 비밀번호가 포함되어 있다면 해싱하여 업데이트
        if (memberDTO.getUserPw() != null && !memberDTO.getUserPw().isEmpty()) {
            String hashedPassword = bCryptPasswordEncoder.encode(memberDTO.getUserPw());
            memberDTO.setUserPw(hashedPassword);
        }
        return memberMapper.update(memberDTO);
    }

    public int deleteUser(String userId) {
        return memberMapper.delete(MemberDTO.builder().userId(userId).build());
    }
}