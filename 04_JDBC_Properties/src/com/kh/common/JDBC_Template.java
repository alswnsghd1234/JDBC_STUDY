package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
		
		/*
		 * 기존방식: JDBC Driver 구문, 내가 접속할 DB의 url정보, 접속할 계정명, 비밀번호 들을 자바소스코드 내에
		 * 			명시적으로 작성함(정적 방식[하드 코딩])
		 * -문제점 : DBMS가 변경이 되었을경우 / 접속할 url, 계정명,비밀번호가 변경되었을 경우 - 자바소스코드를 수정해야한다.
		 * 			수정하기 위해서는 프로그램을 재구동해야하기 때문에 사용자 입장에서는 프로그램이 비정상적으로 종료된것처럼
		 * 			느끼게 된다, + 유지보수가 용이하지 않다.
		 * -해결방식 : DB관련된 정보들을 별도로 관리하는 외부 파일(.properties)로 만들어서 관리한다.
		 * 			외부로 key에 대한 value를 읽어서 반영시킨다.(동적방식)
		 * 
		 * */
		//설정파일을 읽어오기 위한 객체 생성
		Properties prop = new Properties();
		
		//Connection 객체 생성
		Connection conn=null;
		
		try {//prop로부터 load메소드로 경로에 있는 파일을 읽어오겠다.
			prop.load(new FileInputStream("resources/driver.properties"));
			
			//(기존방식)Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(prop.getProperty("driver"));
			////(기존방식)conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			conn=DriverManager.getConnection(prop.getProperty("url"),
											prop.getProperty("username"),
											prop.getProperty("password"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
