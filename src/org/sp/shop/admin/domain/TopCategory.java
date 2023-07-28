package org.sp.shop.admin.domain;

//탑카테고리 테이블의 레코드 1건을 담게될 DTO
public class TopCategory {
	public int topcategory_idx;
	public String topname;
	
	
	public int getTopcategory_idx() {
		return topcategory_idx;
	}
	public void setTopcategory_idx(int topcategory_idx) {
		this.topcategory_idx = topcategory_idx;
	}
	public String getTopname() {
		return topname;
	}
	public void setTopname(String topname) {
		this.topname = topname;
	}
	
	
}
