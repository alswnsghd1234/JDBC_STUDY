package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSelect {

	public static void main(String[] args) {
		//TEST 테이블에 있는 데이터를 조회해보자.
		//SELECT문 -> ResultSet(조회된 객체가 담겨있음)
		
		//필요한 변수 세팅하기
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rset=null;
		
		String sql="SELECT * FROM TEST";
		
		//1)드라이버 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			//3)Statement 객체 생성
			stmt=conn.createStatement();
			//4,5)sql문 전달후 겨로가 받기 ResultSet으로
			rset=stmt.executeQuery(sql); //select문은 executeQuery()
			
			//6)ResultSet에 담긴 데이터 꺼내오기
			
				//현재 참조하는 rset으로부터 어떤 컬럼에 해당하는 값을 어떤 타입에 담을건지 제시
				//db의 컬럼명을 제시해야한다(대소문자 상관x)
				while(rset.next()) { // 커서위치 옮기기
				
				int tno = rset.getInt("TNO"); //TNO에 있는 데이터를 가지고 오겠다.
				String tname = rset.getString("TNAME");
				Date tdate = rset.getDate("TDATE");
				
				System.out.println(tno+","+tname+","+tdate);
				}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				
			}

	}

	}}
