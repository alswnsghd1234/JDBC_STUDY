package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

/*
 * DAO (Data Access Object)
 * Controller에 의해 호출
 * Controller에서 요청받은 작업을 수행하기 위한 클래스
 * DB에 직접적으로 접근 후 해당 SQL문을 실행 및 결과 받기(JDBC)
 * 
 * */

public class MemberDao {
	
	/*
	 * JDBC용 객체
	 * -Connection : DB의 연결정보를 담고 있는 객체(IP주소,PORT번호,계정명,비밀번호)
	 * -(Prepared)Statement : 해당 DB에 SQL문을 전달하고 실행한 후 결과를 받아내는 객체
	 * -ResultSet : 만일 실행한 SQLansdl SELECT문일 경우 조회된 결과들이 담겨있는 객체
	 * 
	 * JDBC 처리 순서
	 * 1)JDBC Driver 등록 : 해당 DBMS가 제공하는 클래스 등록
	 * 2)Connection 생성 ㅣ 접속하고자 하는 DB에 정보를 입력해서 DB에 접속하면서 생성
	 * 3)Statement 생성 : Connection 객체를 이용해서 생성
	 * 4)SQL문을 전달하면서 실행 : Statement 객체를 이용해서 SQL 문을 실행
	 * 						-SELECT 문일 경우 excuteQuery()메소드를 이용해서 실행
	 * 						-나머지 DML일 경우 excutUpdate()메소드를 이용하여 실행
	 * 5)결과 받기
	 * 6_1SELECT 문일 경우 ResultSet객체(조회된 데이터가 담겨있음)로 받는다
	 * 6_2나머지 DML문을 경우 - int형 변수(처리된 행 개수)로 받기
	 * 6_3)DML문)ResultSet 객체에 담긴 데이터들을 하나씩 뽑아서 VO 객체에 담기 (ArrayList로 묶어서 관리하기)
	 * 6_2)DML문)트랜잭션 처리(성공이면 COMMIT;실패면 ROLLBACK;)
	 * 7)사용완료한 JDBC용 객체들 자원 반납(close) -> 생성의 역순으로 진행

	 * */
	
	public int insertMember(Member m) {//INSERT문 작업
		
		//필요한 변수들 선언
		int result =0; //처리된 결과(처리된 행수)를 담을 변수
		Connection conn =null; //접속된 DB에 대한 정보를 담는 변수
		Statement stmt = null; // SQL문 실행 후 결과를 받기위한 변수
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,"
												+"'"+m.getUserId()+"',"
												+"'"+m.getUserPw()+"',"
												+"'"+m.getUserName()+"',"
												+"'"+m.getGender()+"',"
												+	m.getAge()+","
												+"'"+m.getEmail()+"',"
												+"'"+m.getPhone()+"',"
												+"'"+m.getAddress()+"',"
												+"'"+m.getHobby()+"',"
												+"SYSDATE)";
		//1) JDBC 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			stmt.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return result;
	}
	
	//사용자가 전체조회를 요청했을때 SELECT구문을 실행해서 결과를 내뱉는 메소드
	
	public ArrayList<Member> selectAll(){
		
		//필요한 변수 세팅
		//Connection,Statement,ResultSet,ArrayList<Member>
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<>(); //회원정보가 여러개면 여러개의 회원정보를
		
		//실행할 sql문
		String sql="SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt = conn.createStatement();
			//sql문 보내서 결과 받기
			
			rset= stmt.executeQuery(sql);
			
			// 현재 조회결과가 담긴 ResultSet에서 한 행씩 뽑아 VO 객체에 담기(Member)
			
			while(rset.next()) {//커서를 한줄 옮기고 해당 위치에 데이터가 있으면 true 없으면 false
				
				//현재 커서가 가르키고 있는 행의 데이터를 뽑아 Member객체에 담아보기.
				
				Member m = new Member();
				/*
				 * rset 으로 부터 어떤 컬럼에 해당하는 값을 뽑을건지 제시
				 * ->컬럼명(대소문자 가리지 않음), 컬럼순번
				 * ->권장사항 = 컬럼명+대문자
				 * rset.getInt() = int형 값 뽑을때
				 * rset.getString() = String형 값 뽑을때
				 * rset.getDate() = Date형 값 뽑을때
				 * 
				 * */
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPw(rset.getString("USERPW"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				
				// 한 행에 대한 모든 컬럼의 데이터 값을
				//Member 객체 필드에 각각 담음
				
				list.add(m);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list; //행의 데이터를 갖고있는 Member 객체들이 담겨져있는 리스트 반환
	}
	//사용자에게 입력받은 아이디로 해당 회원이 있는지 정보 검색 처리 메소드
	
	public Member searchById(String userId) {
		//필요 도구 생성
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Member m = null;
		
		String sql="SELECT * FROM MEMBER WHERE USERID='"+userId+"'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt = conn.createStatement();
			
			rset=stmt.executeQuery(sql);
			
			if(rset.next()) {//커서가 가르킬 곳에 데이터가 있으면(즉,조회된 결과가 있으면)
				//조회된 행에 있는 컬럼들의 데이터를 Member객체에 한번에 모아서 보낸다.
				//매개변수 생성자로 값 넣기
				m = new Member(rset.getInt("USERNO")
								,rset.getString("USERID")
								,rset.getString("USERPW")
								,rset.getString("USERNAME")
								,rset.getInt("AGE")
								,rset.getString("HOBBY")
								,rset.getString("EMAIL")
								,rset.getString("PHONE")
								,rset.getString("ADDRESS")
								,rset.getDate("ENROLLDATE")
								,rset.getString("GENDER")
								);
			
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			rset.close();
			stmt.close();
			conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		
	}return m;
}

	public ArrayList<Member> searchByName(String userName) {
		
		Connection conn = null;
		Statement stmt =null;
		ResultSet rset = null;
		Member m = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%"+userName+"%'";
		
		ArrayList<Member> list = new ArrayList<>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			
			rset=stmt.executeQuery(sql);
			
			
			
			while(rset.next()) {
				
				
				
			if(rset.next()) {//커서가 가르킬 곳에 데이터가 있으면(즉,조회된 결과가 있으면)
				//조회된 행에 있는 컬럼들의 데이터를 Member객체에 한번에 모아서 보낸다.
				//매개변수 생성자로 값 넣기
				list.add(new Member(rset.getInt("USERNO")
								,rset.getString("USERID")
								,rset.getString("USERPW")
								,rset.getString("USERNAME")
								,rset.getInt("AGE")
								,rset.getString("HOBBY")
								,rset.getString("EMAIL")
								,rset.getString("PHONE")
								,rset.getString("ADDRESS")
								,rset.getDate("ENROLLDATE")
								,rset.getString("GENDER")
								));
			}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		return list;
		}

	public int updateMember(Member m) {
		
		Connection conn = null;
		Statement stmt = null;
		
		int result=0;
		
		String sql="UPDATE MEMBER "
				+"SET USERPW = '"+m.getUserPw()+"'"
				+"	,EMAIL ='"+m.getEmail()+"'"
				+"	,PHONE ='"+m.getPhone()+"'"
				+"	,ADDRESS=  '"+m.getAddress()+"'"
				+" WHERE USERID='"+m.getUserId()+"'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			
			result=stmt.executeUpdate(sql);
			
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
		
		
	}

	public int deleteById(String userId) {
		
		Connection conn = null;
		Statement stmt = null;
		
		int result=0;
		
		String sql="DELETE FROM MEMBER WHERE USERID='"+userId+"'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			
			result=stmt.executeUpdate(sql);
			
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}	
	return result;
	

	}
}

