package com.kh.view;


import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

//View : 사용자가 보게 될 시작적인 요소를 담당한다.

public class MemberView {
	
	//전역으로 쓸 수 있는 Scanner 객체 생성
	
	private Scanner sc = new Scanner(System.in);
	
	//전역으로 MemberController에 요청할 수 있는 객체 생성
	private MemberController mc = new MemberController();

	public void mainMenu() {
		
		while(true) {
			//프로그램 종료 전까지 메뉴 선택 반복하기 위해 while문으로 감싸기
			
			System.out.println("-------------회원관리 프로그램-----------");
			System.out.println("1.회원 추가");
			System.out.println("2.회원 전체 조회");
			System.out.println("3.회원 아이디로 검색");
			System.out.println("4.회원 이름 검색");
			System.out.println("5.회원 정보 변경");
			System.out.println("6.회원 탈퇴");
			System.out.println("0.프로그램 종료");
			System.out.println("------------------");
			System.out.println("원하시는 메뉴번호를 입력하시오.");
			
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: insetMember(); break;
			case 2: selectAll(); break;
			case 3:	searchById();break;
			case 4: searchByName();break;
			case 5:	updateMember();break;
			case 6: deleteMember();break;
			case 0:System.out.println("프로그램을 종료합니다"); return;
			default: System.out.println("잘못된 메뉴번호를 입력하셨습니다.");
			}
			
		}
		

	}
	//2.전체조회 메소드
	private void selectAll() {
		System.out.println("-------회원 전체 조회----------");
		
		//controller에 회원 전체조회 요청
		mc.selectAll();
	}
	//3.아이디 조회
	private void searchById() {
		System.out.println("------회원 아이디 검색-----");
		System.out.println("검색할 회원의 아이디 입력");
		String userId = sc.nextLine();
		
		//입력한 아이디를 가지고 컨트롤러에 요청하기
		mc.searchById(userId);
		
		
	}
	private void searchByName() {
		System.out.println("------회원 이름 검색-----");
		System.out.println("검색할 회원의 이름 입력");
		String userName = sc.nextLine();
		
		mc.searchByName(userName);
	}
	
	//5. 멤버 수정 메소드
	private void updateMember() {
		System.out.println("---------회원 정보 변경----------");
		
		//변경할 회원의 아이디
		System.out.println("변경할 회원의 아이디 :");
		String userId = sc.nextLine();
		
		//변경할 정보들
		System.out.println("변경할 비밀번호 입력 :");
		String userPw = sc.nextLine();
		System.out.println("변경할 이메일 입력 : ");
		String email = sc.nextLine();
		System.out.println("변경할 핸드폰 입력 : ");
		String phone = sc.nextLine();
		System.out.println("변경할 주소 입력 :");
		String address = sc.nextLine();
		
		//회원 정보 수정
		
		mc.upateMember(userId,userPw,email,phone,address);
		
		
	}
	private void deleteMember() {
		//변경할 회원의 아이디
				System.out.println("삭제할 회원의 아이디 :");
				String userId = sc.nextLine();
			mc.deleteMember(userId);
	}
	//회원 추가용 화면
	//추가하고자 하는 회원의 정보를 입력받아서 추가 "요청" 할 수 있는 화면
	public void insetMember() {
		
		System.out.println("----회원 추가----");
		//입력
		//회원번호(시퀀스) enrollDate(default sysdate) 두 컬럼에 대해서는 입력받지 않는다.
		System.out.println("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.println("비밀번호 : ");
		String userPw = sc.nextLine();
		
		System.out.println("이름 : ");
		String userName = sc.nextLine();
		
		System.out.println("성별 : ");
		String gender = sc.nextLine();
		
		System.out.println("나이 : ");
		int age= sc.nextInt();
		sc.nextLine();
		
		
		System.out.println("이메일 : ");
		String email = sc.nextLine();
		
		System.out.println("핸드폰 번호(-없이 숫자만 입력) : ");
		String phone = sc.nextLine();
		
		System.out.println("주소 : ");
		String address = sc.nextLine();
		
		System.out.println("취미 : ");
		String hobby = sc.nextLine();
		
		
		//입력받은 정보를 매개변수로 넘겨서 회원 추가 요청 - Controller에 있는 메소드를 호출한다.
		mc.insertMember(userId,userPw,userName,gender,age,email,phone,address,hobby);
	}
	
	//서비스 요청 성공시 보게 될 화면
	public void displaySuccess(String message) {
		System.out.println("서비스요청 성공"+message);
	}
	//서비스 요청 실패시 보게 될 화면
	public void displayFail(String message) {
		System.out.println("서비스요청 실패"+message);
	}
	//전체 조회 결과가 없을때
	public void displayNodata(String message) {
		
		System.out.println(message);
	}
	//전체 조회 ArrayList<E>	
	public void displayList(ArrayList<Member> list) {
		System.out.println("조회된 결과는"+list.size()+"개 입니다");
		
		for(Member m:list) { //for(반환할 타입 변수명 :반복할 저장소(컬렉션or배열))
			System.out.println(m);
		}
	}
	public void displayOne(Member m) {
		
		System.out.println("조회된 결과는 다음과 같습니다.");
		System.out.println(m);
	}
	

}
