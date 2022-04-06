package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;

public class ProductService {

	public int inserMember(Product p) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new ProductDao().insertProduct(conn,p);
		
		if(result>0) {//성공
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

	public ArrayList<Product> selectAll() {

		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Product> list = new ProductDao().selectAll(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}


	public ArrayList<Product> selectByProductName(String productName) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Product> list = new ProductDao().searchByName(conn, productName);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public int updateMember(Product p) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new ProductDao().updateProduct(conn,p);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}

	public int deleteMember(String ProductId) {

		Connection conn = JDBCTemplate.getConnection();
		
	
		int result = new ProductDao().deleteProduct(conn,ProductId);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}

}
