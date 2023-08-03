package org.sp.shop.client.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.sp.shop.admin.domain.Member;

import util.DBManager;

//Member테이블에 대한 CRUD를 수행하기 위한 객체
public class MemberDAO {
	DBManager dbManager;
	
	public MemberDAO(DBManager dbManager) {
		this.dbManager=dbManager;
	}
	
	public int insert(Member member) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		int result=0; //DML에 대한 성공, 실패를 판단할 수 있는 반환값
		
		con=dbManager.connect();
		
		StringBuilder sb=new StringBuilder();
		sb.append("insert into member(member_idx, id, pass, name, email)");
		sb.append(" values(seq_member.nextval, ?, ?, ?, ?)");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPass());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			
			result=pstmt.executeUpdate(); //DML쿼리실행
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt);
		}
		
		return result;
	}
}
