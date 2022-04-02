package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

/*
 * DAO (Data Access Object)
 * Controller에 의해 호출
 * Controller에서 요청받은 작업을 수행하기 위한 클래스
 * DB에 직접적으로 접근 후 해당 SQL문을 실행 및 결과 받기 (JDBC)
 * */
public class MemberDao {
	
	/*
	 * JDBC용 객체
	 * -Connection : DB의 연결정보를 담고 있는 객체(IP주소,PORT번호,계정명,비밀번호)
	 * -(Prepared)Statement : 해당 DB에 SQL문을 전달하고 실행한 후 결과를 받아내는 객체
	 * -ResultSet : 만일 실행한 SQL문이 SELECT문일 경우 조회된 결과들이 담겨있는 객체
	 * 
	 * #PreparedStatement 란
	 * -SQL문을 바로 실행하지 않고 잠시 보관하는 개념
	 * -미완성된 SQL문을 먼저 전달하고 실행하기 전에 완성형태로 만든 후 실행한다.
	 * -미완성된 SQL문 만들기(사용자가 입력한 값들이 들어갈수있는 공간을 ?(위치홀더)로 확보)
	 * -각 위치홀더에 맞는 값을 세팅해준다.
	 * 
	 * #Statement의 자식 객체이다 
	 * 차이점
	 * 1) Statement는 완성된 SQL문을 작성, PreparedStatement는 미완성된 SQL문에 ?(위치홀더)를 사용해서 작성
	 * 2) Statement로 작성시 stmt=conn.createStatement();
	 * 2-1)PreparedStatement로 작성시 pstmt=conn.preparedStatement(SQL);
	 * 3) Statement로 SQL문 실행시 - stmt.excuteXXXX(SQL)
	 * 3-1)PreparedStatement로 작성시 pstmt.setString(?위치,값); 
	 * 								pstmt.setInt(?위치,값);
	 * 								결과 =pstmt.executeXXX();
	 * 
	 * 
	 * 
	 * */

	public int insertMember(Connection conn, Member m) {
		
		int result = 0; 
		PreparedStatement pstmt = null; 
		//INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,'XXX','XXX','XXX',...SYSDATE);
		String sql ="INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,DEFAULT)";
		
		try {
		
			pstmt=conn.prepareStatement(sql);
			
			//미완성된 SQL문 완성시켜주기
			
			pstmt.setString(1,m.getUserId());
			pstmt.setString(2,m.getUserPw());
			pstmt.setString(3,m.getUserName());
			pstmt.setString(4,m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result=pstmt.executeUpdate();
			
			if(result>0) { //1개이상의 행이 삽입되었다면(성공)
				conn.commit(); //성공했으니 commit으로 확정
			}else {
				conn.rollback(); //실패했으니 rollback으로 되돌리기
			}
		
//		} catch (ClassNotFoundExeption e) {
//			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result; //DML문이 작업된 행수 
	}
	
	
	//사용자가 전체조회를 요청했을때 SELECT구문을 실행해서 결과를 내뱉는 메소드
	
	public ArrayList<Member> selectAll(){
		
		
		//필요한 변수 세팅
		//Connection,Statement,ResultSet,ArrayList<Member>
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		//회원정보가 여러개면 여러개의 회원정보를 담아야 하니까 ArrayList에 담아준다.
		ArrayList<Member> list = new ArrayList<>(); //빈 리스트 생성
		
		//실행할 sql문 
		String sql="SELECT * FROM MEMBER";
		
		try {
			//드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//연결객체 생성
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			//Statement객체 생성
			stmt=conn.createStatement();
			
			//sql문 보내서 결과 받기
		
			rset=stmt.executeQuery(sql);
			
			//현재 조회결과가 담긴 ResultSet에서 한 행씩 뽑아 VO객체에 담기(Member)
			
			while(rset.next()) {//커서를 한줄 옮기고 해당 위치에 데이터가 있으면 true 없으면 false
			
				//현재 커서가 가르키고 있는 행의 데이터를 뽑아 Member객체에 담아보기.
				Member m = new Member();
				/*
				 * rset으로 부터 어떤 컬럼에 해당하는 값을 뽑을건지 제시
				 * ->컬럼명(대소문자 가리지 않음), 컬럼순번
				 * ->권장사항 = 컬럼명+대문자
				 * rset.getInt() = int형 값 뽑을때
				 * rset.getString() = String형 값 뽑을때
				 * rset.getDate() = Date형 값 뽑을때 
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
				//한 행에 대한 모든 컬럼의 데이터값을 
				//Member객체 필드에 각각 담음
				list.add(m);
			}
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			//자원 반납하기
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;  // 행의 데이터를 갖고있는 Member객체들이 담겨져있는 리스트 반환 
	}
	
	
	//사용자에게 입력받은 아이디로 해당 회원이 있는지 정보 검색 처리 메소드
	
	public Member searchById(String userId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Member m = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID =?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset=pstmt.executeQuery();
		
			if(rset.next()) {
				m=new Member(
							 rset.getInt("USERNO")
							,rset.getString("USERID")
							,rset.getString("USERPW")
							,rset.getString("USERNAME")
							,rset.getString("GENDER")
							,rset.getInt("AGE")
							,rset.getString("EMAIL")
							,rset.getString("PHONE")
							,rset.getString("ADDRESS")
							,rset.getString("HOBBY")
							,rset.getDate("ENROLLDATE")
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}

	public ArrayList<Member> searchByName(String userName) {
		
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		
		ArrayList<Member> list = new ArrayList<>();
		
//		String sql ="SELECT*FROM MEMBER WHERE USERNAME LIKE ?";
//		pstmt.setString(1,"%"+userName+"%") 
		String sql ="SELECT*FROM MEMBER WHERE USERNAME LIKE '%'||?||'%'";
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1,userName);
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(
							 rset.getInt("USERNO")
							,rset.getString("USERID")
							,rset.getString("USERPW")
							,rset.getString("USERNAME")
							,rset.getString("GENDER")
							,rset.getInt("AGE")
							,rset.getString("EMAIL")
							,rset.getString("PHONE")
							,rset.getString("ADDRESS")
							,rset.getString("HOBBY")
							,rset.getDate("ENROLLDATE")
							));
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return list;
		
	}
//
//
	public int updateMember(Member m) {
		
		Connection conn=null;
		PreparedStatement pstmt =null;
		int result = 0;
		String sql="UPDATE MEMBER"
				+ "	SET"
				+ "		USERPW=?"
				+ ",	EMAIL=?"
				+ ",	PHONE=?"
				+ ",	ADDRESS=?"
				+ "WHERE USERID=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
												"JDBC","JDBC");
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1,m.getUserPw());
			pstmt.setString(2,m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result=pstmt.executeUpdate();
			
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	public int deleteMember(String userId) {
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		String sql="DELETE FROM MEMBER WHERE USERID=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result=pstmt.executeUpdate();
			
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	
	}
}
