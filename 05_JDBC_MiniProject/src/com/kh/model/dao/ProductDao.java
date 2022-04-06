package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;
import com.kh.model.vo.Product;

public class ProductDao {
	
	private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int insertProduct(Connection conn, Product p) {
		
		int result =0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertProduct");
		
		
		
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,p.getProductId());
			pstmt.setString(2, p.getProductName());
			pstmt.setInt(3, p.getPrice());
			pstmt.setString(4, p.getDescription());
			pstmt.setInt(5, p.getStock());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			//PreparedStatement 객체 반납
			JDBCTemplate.close(pstmt);
	
			}
			return result;
			}

	public ArrayList<Product> selectAll(Connection conn) {
		
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Product> list = new ArrayList<>();
		
		String sql = prop.getProperty("selectAll");
		
		try {
			stmt = conn.createStatement();
			rset= stmt.executeQuery(sql);
			
			while(rset.next()) {
				
				Product p = new Product();
				
				
				p.setString(1, p.getProductId());
				pstmt.setString(2, p.getProductName());
				pstmt.setInt(3, p.getPrice());
				pstmt.setString(4, p.getDescription());
				pstmt.setInt(5, p.getStock());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<Product> searchByName(Connection conn, String productName) {
		return null;
	}

	public int updateProduct(Connection conn, Product p) {
		
		int result= 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateMember");
		try {
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, p.getProductId());
		pstmt.setString(2, p.getProductName());
		pstmt.setInt(3, p.getPrice());
		pstmt.setString(4, p.getDescription());
		pstmt.setInt(5, p.getStock());
				
		result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
		
		
	}

	public int deleteProduct(Connection conn, String productId) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteProduct");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		
		return 0;
	}

	}


