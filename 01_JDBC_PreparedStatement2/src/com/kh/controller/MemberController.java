package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

/*
 * Controller : View를 통해서 요청한 기능을 담당
 * 				해당 메소드로 전달된 데이터들을 가공처리(VO객체에 담아서) Dao 메소드를 호출한다.
 * 				Dao로 반환받은 결과에 따라 사용자가 보게될 화면을 지정한다(View에 있는 메소드 호출)
 * 
 * 
 * */


public class MemberController {

	public void insertMember(String userId, String userPw, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		
		//1.전달된 데이터들을 Member 객체에 담기 (가공처리)
		Member m = new Member(userId,userPw,userName,age,gender,email,phone,address,hobby);
		
		//2.Dao의 insertMember 메소드 호출(가공된 멤버객체를 보낸다.)
		int result=new MemberDao().insertMember(m);
		
		//3. Dao에서 작업한 결과값에 딸 View에 보여질 화면을 정해준다.
		if(result>0) {
			//성공했으니 성공메시지를 보낸다
			new MemberView().displaySuccess("회원 추가 성공");
		}else {
			//실패했으니 실패메세지를 보낸다
			new MemberView().displayFail("회원 추가 실패");
		}
	}
	
	//사용자의 회원 전체 조회 요청을 처리해주는 메소드
	public void selectAll() {
		
		//view에서 controller를 전체조회해줘
		//Dao야 너가 DB에서 전체 조회해와
		//조회해서 나한테 결과 보내줘
		//결과값을 담을 변수
		//SELECT-ResultSet->ArrayList<Member>
		
		ArrayList<Member> list = new MemberDao().selectAll();
		//DAO에서 작업을 끝마친 결과를 전달받아 해당 결과를 토대로
		//어떠한 화면을 보여줄지 정해서 VIEW에 전달한다.
		
		//조회결과가 있는지 없는지 판단 후 사용자가 보게될 화면 지정
		if(list.isEmpty()) { //list가 비어있니?
			new MemberView().displayNodata("전체 조회 결과가 없습니다.");
		}else {//조회가 된 경우->조회결과있음(list에 Member 객체가 담겨있음)
			new MemberView().displayList(list);
		}
	}
	//사용자가 입력한 아이디로 검색 요청을 처리해주는 메소드
	public void searchById(String userId) {
		
		new MemberDao().searchById(userId); //m (조회된 회원정보)
		
		Member m = new MemberDao().searchById(userId);
		if(m==null) {
			new MemberView().displayNodata(userId+"에 해당하는 회원정보가 없습니다.");
		}else {
			new MemberView().displayOne(m);
		}
	}

	public void searchByName(String userName) {
		
		new MemberDao().searchByName(userName);
		
		ArrayList<Member> list  = new MemberDao().searchByName(userName);
		if(list==null) {
			new MemberView().displayNodata(userName+"은 없다");
		}else {
			new MemberView().displayList(list);
		}
		
	}

	public void upateMember(String userId, String userPw, String email, String phone, String address) {

		Member m = new Member();
		
		m.setUserId(userId);
		m.setUserPw(userPw);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address);
		
		int result = new MemberDao().updateMember(m);
		
		if(result>0) {
			//성공했으니 성공메시지를 보낸다
			new MemberView().displaySuccess("회원 정보 변경 성공");
		}else {
			//실패했으니 실패메세지를 보낸다
			new MemberView().displayFail("회원 정보 변경 실패");
		}
	}

	public void deleteMember(String userId) {
		
		new MemberDao().deleteById(userId); //m (조회된 회원정보)
		
		int result = new MemberDao().deleteById(userId);
		if(result>0) {
			//성공했으니 성공메시지를 보낸다
			new MemberView().displaySuccess("회원 삭제 성공");
		}else {
			//실패했으니 실패메세지를 보낸다
			new MemberView().displayFail("회원 삭제 실패");
		}
	}


}
