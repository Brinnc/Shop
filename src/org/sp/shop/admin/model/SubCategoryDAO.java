package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.shop.admin.domain.SubCategory;

//오직 서브카테고리 테이블에 대한 CRUD만을 담당하는 객체
public class SubCategoryDAO {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="shop";
	String pass="1234";

	public List selectAllByFKey(int topcategory_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List list=new ArrayList();
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, pass);
			
			String sql="select * from subcategory where topcategory_idx=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, topcategory_idx); //바인드변수값 지정
			rs=pstmt.executeQuery(); //실행
			
			//rs가 곧 닫히므로 rs를 대신할 수 있는 DTO에 담은 후 , 여러건이므로 List에 담아야함
			while(rs.next()) {
				SubCategory sub=new SubCategory();
				sub.setSubcategory_idx(rs.getInt("subcategory_idx"));
				sub.setTopcategory_idx(rs.getInt("topcategory_idx"));
				sub.setSubname(rs.getString("subname"));
				
				list.add(sub); //리스트에 추가하기
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return list;
	}
}
