package org.sp.shop.admin.view;

import java.awt.Dimension;

import javax.swing.JPanel;

//모든 페이지의 공통적인 기능을 보유한 최상단 클래스 정의
public class Page extends JPanel{
	
	//모든 페이지는 너비, 높이가 공통적임
	public Page() {
		setPreferredSize(new Dimension(1100, 570));
		setVisible(false);
	}
}
