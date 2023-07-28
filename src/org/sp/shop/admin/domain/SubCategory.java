package org.sp.shop.admin.domain;

public class SubCategory {
	private int subcategory_idx;
	private String subname;
	private int topcategory_idx;
	
	
	public int getSubcategory_idx() {
		return subcategory_idx;
	}
	public void setSubcategory_idx(int subcategory_idx) {
		this.subcategory_idx = subcategory_idx;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public int getTopcategory_idx() {
		return topcategory_idx;
	}
	public void setTopcategory_idx(int topcategory_idx) {
		this.topcategory_idx = topcategory_idx;
	}
	
	
	
}
