package com.kh.view.ProductView;

import java.util.Scanner;

import com.kh.controller.ProductController.ProductController;


public class ProductView {
	
	private Scanner sc = new Scanner(System.in);
	
	private ProductController pc = new ProductController();

	public void mainView() {
		

		while(true) {
			//프로그램 종료 전까지 메뉴 선택 반복하기 위해 while문으로 감싸기
			
			System.out.println("-------------회원관리 프로그램-----------");
			System.out.println("1.상품 전체 조회");
			System.out.println("2.상품 추가");
			System.out.println("3.상품명 검색 하기");
			System.out.println("4.상품 정보 수정 하기");
			System.out.println("5.상품 삭제 하기");
			System.out.println("0.프로그램 종료");
			System.out.println("------------------");
			System.out.println("원하시는 메뉴번호를 입력하시오.");
			
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: selectAll(); break;
			case 2: insetProduct(); break;
			case 3:	searchByProductName();break;
			case 4: updateProduct();break;
			case 5:	deleteProduct();break;
			case 0:System.out.println("프로그램을 종료합니다"); return;
			default: System.out.println("잘못된 메뉴번호를 입력하셨습니다.");
			}
			
		}
		
	}

	private void selectAll() {
		System.out.println("----모든 상품 조회-----");
	}
	private void insetProduct() {
		
		System.out.println("--- 상품 추가 하기 ---");
		System.out.println("아이디 : ");
		String productId = sc.nextLine();
		
		System.out.println("이름 : ");
		String productName = sc.nextLine();
		
		System.out.println("가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		
		System.out.println("설명 : ");
		String desciption = sc.nextLine();
		
		System.out.println("재고 ");
		int stock= sc.nextInt();
		sc.nextLine();

		//입력받은 정보를 매개변수로 넘겨서 회원 추가 요청 - Controller에 있는 메소드를 호출한다.
		pc.insertProduct(productId,productName,price,desciption,stock);
	}
	private void searchByProductName(String productName) {
		System.out.println("---찾을 상품 이름을 입력하세요---");
	}
	private void updateProduct(String productId) {
		System.out.println("---변경 할 상품의 아이디를 입력하세요---");
	}
	private void deleteProduct(String productId) {
		System.out.println("---삭제 할 상품의 아이디를 입력하세요---");
	}




}
