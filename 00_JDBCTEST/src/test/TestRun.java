package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRun {
	
	public static void main(String[] agrs) {
		
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
		
		//INSERT 문 -> 처리된 행수(int)
		
		int result=0; //결과(처리된 행수)를 받아놓은 변수
		Connection conn = null; //DM에 연결정보를 보관할 객체
		Statement stmt = null; //sql문 전달해서 실행후 결과 객체
		
		//실행할 sql문 작성(맨뒤에 세미콜론이 없어야된다.)
		String sql="DELETE FROM TEST WHERE TNO=1 ";
		
		//1)jdbc driver 등록(oracle.jdbc.driver.OracleDriver)
		
		try {
			
			//1)jdbc driver 등록(oracle.jdbc.driver.OracleDriver)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("jbdc driver 등록 성공");
			
			//2)Connection 객체 생성 : DB에 연결(url,계정명,비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			//3)Statement 생성
			stmt = conn.createStatement();
			
			//4,5)sql문을 전달하면서 실행 후 결과 받기(처리된 행수)
			result = stmt.executeUpdate(sql);
			//6)트랜잭션처리
			if(result>0) { //성공했을 경우(처리된 행수가 1이라도 있을경우)
				conn.commit();
			}else {//실패했을 경우
				conn.rollback();
			}
		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7)다른 객체들 반납하기 (생성 역순)
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result>0) {
			System.out.println("성공적으로 삽입되었습니다.");
		}else {
			System.out.println("데이터 삽입 실패");
		}
	}
}
