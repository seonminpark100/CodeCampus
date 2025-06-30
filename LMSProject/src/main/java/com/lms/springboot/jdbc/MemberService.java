package com.lms.springboot.jdbc;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService { 
    private final IMemberService memberMapper; 
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(IMemberService memberMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberMapper = memberMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<MemberDTO> getAllUsers() { 
        return memberMapper.select(); 
    }

    @Transactional
    public MemberDTO createUser(MemberDTO memberDTO) { // MemberDTO 사용
        
        MemberDTO existingUser = memberMapper.selectOne(MemberDTO.builder().userId(memberDTO.getUserId()).build());
        if (existingUser != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

       
        String hashedPassword = bCryptPasswordEncoder.encode(memberDTO.getUserPw());
        memberDTO.setUserPw(hashedPassword);

        
        if (memberDTO.getJoinDate() == null) {
            memberDTO.setJoinDate(LocalDate.now());
        }

        
        int result = memberMapper.insert(memberDTO);
        if (result > 0) {
            return memberDTO; 
        } else {
            throw new RuntimeException("사용자 생성에 실패했습니다.");
        }
    }

   
    public MemberDTO getUserById(String userId) {
        return memberMapper.selectOne(MemberDTO.builder().userId(userId).build());
    }

    public int updateUser(MemberDTO memberDTO) {
       
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