package com.example.semiproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semiproject.dto.MemberDTO;
import com.example.semiproject.mapper.MemberMapper;

@Service
public class MemberService {

   @Autowired
   private MemberMapper memberMapper;
   
//   [회원가입] 서비스 메서드명은 sql 명령어(insert)가 아닌 
//   기능 의미(join)로 짓는다  
//   => "insertMember" 보다 "join"이 "회원가입"이라는 의미를 더 잘 표현
   public void join(MemberDTO memberDTO) {
      memberMapper.insertMember(memberDTO);
   }
   
//   [아이디 중복 확인] 반환값이 true라면 이미 사용중인 아이디
   public boolean isLoginIdDuplicated(String loginId) {
	 //해당 id를 가진 회원이 있다면 해당 회원의 정보(1개의 행)가 존재한다는 뜻.
	   //1개의 행이 존재한다면 true, 존재하지 않는다면 false를 반환
      return memberMapper.countByLoginId(loginId) > 0;
   }
   
//   [로그인] 일치하는 회원이 있으면 MemberDTO를 반환, 없으면 null
   public MemberDTO login(String loginId, String pwd) {
	   return memberMapper.selectByLoginIdAndPwd(loginId, pwd);
   }
   
}










