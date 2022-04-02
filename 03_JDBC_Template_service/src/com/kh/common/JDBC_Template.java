package com.kh.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Template {
	
	/*
	 * 
	 * JDBC 과정 중 반복적으로 쓰이는 구문들이 각각의 메소드로 정의해둘 공간
	 * "재사용을 목적으로 한다.
	 * 이 클래스에서의 모든 메소드들은 다 static메소드로 만든다.
	 * 싱글톤 패턴 : 메모리영역에 단 한번만 올라간것을 재사용하는 개념
	 * 
	 * 공통적인 부분 뽑아내기
	 * 1.DB와 접속된 Connection 객체를 생성해서 반환해주는 메소드.
	 * 
	 * 
	 * */
	
	public static Connection getConnection() {
		
		//Connection 객체 생성
		Connection conn=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
		//2.전달받은 JDBC용 객체를 반납시켜주는 메소드(각 객체별로 따로 만든다)
		//2_1)Connection 객체 반납 메소드
	
	public static void close(Connection conn){
		
		try {
			//conn이 null이면 nullPointerException이 발생하기 때문에 조건으로 확인해야된다.
			if(conn!=null&&!conn.isClosed())
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//2_2)Statement 객체 반납 메소드(PreparedStatement객체의 자식이기때문에 다형성이 적용되어 매개변수로 전달 가능)
	
	public static void close(Statement stmt) {
		
		try {
			
			if(stmt!=null&&!stmt.isClosed())
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void close(ResultSet rset) {
		
		try {
			
			if(rset!=null&&!rset.isClosed())
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//3. 전달받은 Connection 객체를 이용해서 트랜잭션 처리를 해주는 메소드
	
	public static void commit(Connection conn) {
		
		try {
			if(conn!=null&&conn.isClosed()) {
			conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//rolback메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn!=null&&conn.isClosed()) {
				conn.rollback();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
