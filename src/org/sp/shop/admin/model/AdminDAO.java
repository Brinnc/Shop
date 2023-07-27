package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sp.shop.admin.domain.Admin;

//오직 어드민 테이블에 대한 CRUD만을 담당하기 위한 객체
public class AdminDAO {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="shop";
	String pass="1234";
	
	//아래의 로그인 메서드를 호출하는 사람에게 그 결과를 알려줘야함
	//어떤 식의 결과? 즉, 로그인을 성공한 사람에게는 그 사람을 시스템이 기억해줘야함
	public Admin login(Admin admin) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		Admin dto=null; //로그인 후 해당 관리자 한 사람의 정보를 담기 위한 객체
		
		try {
			//1) 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2) 드라이버 접속
			con=DriverManager.getConnection(url, user, pass);
			if(con==null) {
				System.out.println("접속실패");
			}else {
				//jdbc 프로그래밍 시 컬럼의 값은 바인드 변수로 처리가 가능함
				//이 때 바인드 변수를 jdbc로 표현할 때는 <?>를 이용함
				//바인드 변수란? 데이터베이스 성능을 향상시키기 위한 기법
				String sql="select * from admin where id=? and pass=?";
				pstmt=con.prepareStatement(sql); //3) 쿼리 수행 객체 생성
				pstmt.setString(1, admin.getId()); //문장에서 1번째로 발견된 물음표
				pstmt.setString(2, admin.getPass()); //문장에서 2번째로 발견된 물음표
				
				//4) 쿼리 실행
				rs=pstmt.executeQuery(); //쿼리 수행 후 표를 반환받고, 그 표를 ResultSet객체로 받음
				if(rs.next()) { //커서를 한칸 이동 시 true가 반환된다면 레코드가 존재한다는 것이므로, 로그인 성공으로 판단
					dto=new Admin(); //비어있는 DTO인스턴스 생성
					//채워넣기
					dto.setAdmin_idx(rs.getInt("admin_idx"));
					dto.setId(rs.getString("id"));
					dto.setPass(rs.getString("pass"));
					dto.setName(rs.getString("name"));
					
				}
				//int result=pstmt.executeUpdate(); 
				//DML 수행 후, 이 수행에 의해 영향을 받은 레코드 수가 반환됨
				//따라서 insert의 경우는 성공 시 언제나 1이 반환됨
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(pstmt!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return dto;
		
	}
	
}
