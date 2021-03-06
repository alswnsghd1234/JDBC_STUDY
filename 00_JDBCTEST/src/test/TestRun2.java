package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestRun2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("번호 : ");
		int num = sc.nextInt();
		sc.nextLine();
		System.out.println("이름 : ");
		String name = sc.nextLine();
		
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		System.out.println("입력 기기");
		String sql="INSERT INTO TEST VALUES("+num+",'"+name+"',SYSDATE)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("JDBC 등록 성공");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			finally {
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
