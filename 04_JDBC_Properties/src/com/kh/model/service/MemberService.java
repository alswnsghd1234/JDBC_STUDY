package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBC_Template;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {

	/*
	 * Service
	 * 기존에 DAO의 역할을 분담
	 * 컨트롤러에서 서비스 호출(Connection 객체 생성) 후 서비스를 거쳐서 DAO로 넘어갈것
	 * DAO 호출시 커넥션 객체와 기존에 넘기고자하는 매개변수를 같이 넘겨준다.
	 * DAO 처리가 끝나면 서비스단에서 결과에 따른 트랜잭션 처리도 해준다.
	 * ->Service단을 추가함으로서 DAO에는 순수하게 SQL문을 처리하는 로직만 남게된다.
	 * 
	 * */
	
	
	
	public int insertMember(Member m) {
		
		//Connection 객체 만들어주기
		Connection conn = JDBC_Template.getConnection();
		
		//DAO를 호출하는데 Controller한테 전달받은 요청갑소가 커넥션을 같이 넘긴다.
		int result = new MemberDao().insertMember(conn,m);
		
		//결과에 따른 트랜잭션 처리 해주기
		
		if(result>0) {//성공
			JDBC_Template.commit(conn);
		}else {
			JDBC_Template.rollback(conn);
		}
		JDBC_Template.close(conn);
		
		return result;
	}

	public ArrayList<Member> selectAll() {

		Connection conn = JDBC_Template.getConnection();
		
		ArrayList<Member> list = new MemberDao().selectAll(conn);
		
		JDBC_Template.close(conn);
		
		return list;
	}

	public Member searchById(String userId) {
		
		Connection conn=JDBC_Template.getConnection();
		
		Member m = new MemberDao().searchById(conn,userId);
		
		JDBC_Template.close(conn);
		
		return m;
	}

	public ArrayList<Member> searchByName(String userName) {
		
		Connection conn = JDBC_Template.getConnection();
		
		ArrayList<Member> list = new MemberDao().searchByName(conn, userName);
		
		JDBC_Template.close(conn);
		
		return list;
	}

	public int updateMember(Member m) {
		
		Connection conn = JDBC_Template.getConnection();
		
		int result = new MemberDao().updateMember(conn,m);
		
		if(result>0) {
			JDBC_Template.commit(conn);
		}else {
			JDBC_Template.rollback(conn);
		}
		return result;
	}

	public int deleteMember(String userId) {

		Connection conn = JDBC_Template.getConnection();
		
	
		int result = new MemberDao().deleteMember(conn,userId);
		
		if(result>0) {
			JDBC_Template.commit(conn);
		}else {
			JDBC_Template.rollback(conn);
		}
		return result;
	}

}
