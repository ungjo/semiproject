package com.example.semiproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
//	board테이블 1행의 데이터를 담는 클래스
//	memberLoginId는 Board테이블 컬럼은 아니지만
//	SQL member테이블과 join해서 가져오는 값을 담기 위해
	private int boardId;	// 게시글 번호(pk)
	private String boardTitle;	// 게시글 제목
	private String boardContent;	// 내용
	private int boardHit;	// 조회수
	private String boardDate;	// 작성일시
	private int memberId;	// 작성자 번호(fk)
	
//	DB 컬럼에는 없지만 join으로 가져오는 필드
	private String memberLoginId;	// 작성자 아이디
}