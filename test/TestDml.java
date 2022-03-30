package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestDml {

	public static void main(String[] args) {

		//데이터베이스에 테이블 student 테이블 만들고 컬럼은 sno,sname,sgrade,senroll
		//데이터 3개 insert 하고 delete로 1개 update로 1개 변경해보기
		//마지막은 조회까지 진행
		Scanner sc = new Scanner(System.in);

		int result=0;
		Connection conn = null;
		Statement stmt =null;
		ResultSet rset=null;
		
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("성공");
			
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt = conn.createStatement();
		
			while(true) {
			
			System.out.println("1.생성 \n 2.삭제 \n 3.수정 \n 4.조회 \n 5.종료");
			int num1 = sc.nextInt();
			
			switch(num1) {
			case 1:
				System.out.println("번호 : ");
				int num = sc.nextInt();
				sc.nextLine();
				System.out.println("이름 : ");
				String name = sc.nextLine();
				System.out.println("등급 :");
				String sgrade = sc.nextLine();
				
				String sql = "INSERT INTO STUDENT VALUES('"+num+"','"+name+"','"+sgrade+"',SYSDATE)";
				result=stmt.executeUpdate(sql);
				
				if(result>0) {
					System.out.println("생성완료");
				}else {
					System.out.println("생성실패");
				}
			break;
			case 2:
				System.out.println("지울 번호 : ");
				int num11 = sc.nextInt();
				sc.nextLine();
					String sql1 = "DELETE FROM STUDENT WHERE SNO='"+num11+"'";
					result=stmt.executeUpdate(sql1);
					
					if(result>0) {
						System.out.println("삭제완료");
					}else {
						System.out.println("삭제실패");
					}
			break;
			case 3:
				System.out.println("바꿀 아이디 입력해라");
				int num12 = sc.nextInt();
				sc.nextLine();
				System.out.println("뭘로 변경할래?");
				String name1 = sc.nextLine();
				
					String sql2 = "UPDATE STUDENT SET SGARADE='"+name1+"' WHERE SNO='"+num12+"'";
					result=stmt.executeUpdate(sql2);
					
					if(result>0) {
						System.out.println("수정완료");
					}else {
						System.out.println("수정실패");
					}
				
			break;
			case 4:
				String select="SELECT*FROM STUDENT ORDER BY SNO";
				rset=stmt.executeQuery(select);
				while(rset.next()) { // 커서위치 옮기기
					
					int sno = rset.getInt("SNO"); //TNO에 있는 데이터를 가지고 오겠다.
					String sname = rset.getString("SNAME");
					String sgrade2 = rset.getString("SGARADE");
					Date sdate = rset.getDate(4);
					
					System.out.printf("%d,%s,%s,%s\n",sno,sname,sgrade2,sdate);
					}
				break;
			
			case 5:
				System.out.println("종료");
				return;
			
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		}
	}


