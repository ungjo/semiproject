package com.example.semiproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.semiproject.dto.MemberDTO;
import com.example.semiproject.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

   @Autowired
   private MemberService memberService;
   
   // ---------------------------------
   // [회원가입 폼] GET /member/join
   @GetMapping("/join")	//주소창에 http://localhost:8810/member/join을 입력(GET요청)했을 때 joinForm()메서드 실행
   public String joinForm() {	//return 할 파일의 이름이 문자이기 때문에 String타입 사용
      return "member/join";	//templates.member의 join.html을 의미. 이 파일을 찾아서 화면에 띄움.
   }
   
   // [회원가입 처리] POST /member/join
// 회원가입 폼에 정보를 입력하고 제출했을 때 POST방식으로 요청되는 /join주소를 아래의 메서드가 담당
   @PostMapping("/join")
   public String join(MemberDTO memberDTO, Model model) {
//	    Model은 데이터를 담아서 보낼 때 사용. Model 객체는 Controller에서 생성된 데이터를 View로 전달할 때 사용하는 객체다.
//      아이디 중복 확인 - DB에 이미 같은 아아디가 있다면 가입 불가
//      ?error=duplicate 파라미터를 붙여서 리다이렉트
//      join.html에서 th:if="${param.error == 'duplicated'}"로
//      오류 메시지를 표시
      if(memberService.isLoginIdDuplicated(memberDTO.getMemberLoginId())) {
//         중복된 아이디가 있는 경우
//    	  server(java)가 화면으로 데이터를 보낼 때 'error'라는 이름으로 'duplicate'문자를 담아서 보냄. addAttribute(name, value).
         model.addAttribute("error", "duplicate");
         return "member/join";
      }
//    중복되는 아이디가 없다면 로그인 페이지로 이동
      memberService.join(memberDTO);
      return "redirect:/member/login";
   }
   
//   ---------------------------------------
//   [로그인 폼] GET /member/login
   @GetMapping("/login")
   public String loginForm() {
	   return "member/login";
   }
   
//   [로그인 처리]POST /member/login
//   RequestParam("loginId") - 폼 input과 name속성값으로 파라미터를 받음
//   login.html의 name="loginId"와 반드시 일치해야함
   @PostMapping("/login")
   public String login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd, HttpSession session, Model model) {
//	   DB에서 아이디, 비밀번호 일치여부 확인
	   MemberDTO loginMember = memberService.login(loginId, pwd);
	   
	   if(loginMember == null) {
//		   로그인 실패 - model에 model.addAttribute("error", "fail")을 담아서 로그인 화면으로 이동
		   model.addAttribute("error", "fail");
		   return "member/login";
	   }
	   
//	   로그인 성공 - 세션에 회원 정보를 저장
//	   "loginMember"라는 이름으로 저장해두면 모든 요청에서 꺼낼 수 있음
	   session.setAttribute("loginMember", loginMember);
//	   System.out.println(session.getAttribute("loginMember"));
	   return "redirect:/board/list";
   }
   
//   --------------------------------------------------------
//   [로그아웃] GET /member/logout
   @GetMapping("/logout")
   public String logout(HttpSession session) {
//	   세션에 저장된 모든 데이터를 삭제(로그인 정보 포함)
	   session.invalidate();
//	   로그아웃 후 게시판 목록 페이지로 이동
	   return "redirect:/board/list";
   }
}












