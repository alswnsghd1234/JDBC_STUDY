package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductView;

public class ProductController {

	public void insertProduct(String productId, String productName, int price, String desciption, int stock) {
		Product p = new Product(productId,productName,price,desciption,stock);
		
		int result = new ProductService().inserMember(p);
		
		if(result>0) {
			//성공했으니 성공메시지를 보낸다
			new ProductView().displaySuccess("회원 추가 성공");
		}else {
			//실패했으니 실패메세지를 보낸다
			new ProductView().displayFail("회원 추가 실패");
		}
	}

	public void selectAll() {
		
		ArrayList<Product> list =new ProductService().selectAll();
		
		if(list.isEmpty()) { //list가 비어있니?
			new ProductView().displayNodata("전체 조회 결과가 없습니다.");
		}else {//조회가 된 경우->조회결과있음(list에 Member 객체가 담겨있음)
			new ProductView().displayList(list);
		}
	}
	public void selectByProductName(String keyword) {
		
	}
	public void updateProduct(String productId,String name, int price, String description, int stock) {
		
	}
	public void deleteProduct(String productId) {
		
	}

}
