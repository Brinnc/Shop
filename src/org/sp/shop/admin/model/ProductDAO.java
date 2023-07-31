package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.sp.shop.admin.domain.Product;

import util.DBManager;

//오직 Product테이블에 대한 CRUD를 담당할 DAO객체 정의
public class ProductDAO {
	DBManager dbManager;
	//Product product;
	
	public ProductDAO(DBManager dbManager) {
		this.dbManager=dbManager;
		
	}
	
	//상품 1건 등록
	public int insert(Product product) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=dbManager.connect();
	
		StringBuilder sb=new StringBuilder();
		sb.append("insert into product(product_idx, product_name");
		sb.append(", brand, price, filename, detail");
		sb.append(", subcategory_idx) values(seq_product.nextcal, ?, ?, ?, ?, ?, ?)");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			//실행 전에 바인드 변수값이 먼저 할당되어야 함
			pstmt.setString(1, product.getProduct_name());
			pstmt.setString(2, product.getBrand());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getFilename());
			pstmt.setString(5, product.getDetail());
			pstmt.setInt(6, product.getSubcategory_idx());
			
			result=pstmt.executeUpdate(); //쿼리 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
		
	}
}
