package org.sp.shop.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.sp.shop.admin.view.product.ExcelPage;
import org.sp.shop.admin.view.product.ListPage;
import org.sp.shop.admin.view.product.ProductSubPage;
import org.sp.shop.admin.view.product.RegistPage;

public class ProductPage extends Page {
	JPanel p_west;
	JPanel p_center;
	String[] subTitle = { "상품목록", "상품등록", "엑셀업로드" };
	ArrayList<JLabel> subNavi;

	// 상품등록, 상품목록, 엑셀관리 등등 페이지들을 보유
	ProductSubPage[] pages;

	public ProductPage() {
		p_west = new JPanel();
		p_center = new JPanel();
		createNavi();
		pages = new ProductSubPage[3];
		pages[0] = new ListPage();
		pages[1] = new RegistPage();
		pages[2] = new ExcelPage();

		// 스타일
		p_west.setPreferredSize(new Dimension(150, 600));
		p_west.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), "상품관리"));
		p_center.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), "상품정보"));

		setLayout(new BorderLayout());

		// 조립
		for (int i = 0; i < pages.length; i++) {
			p_center.add(pages[i]);
		}

		add(p_west, BorderLayout.WEST);
		add(p_center);

		// 디폴트로 보여질 페이지
		showHide(0);
		
		//좌측 네비게이션에 링크 걸기
		for(int i=0; i<subNavi.size(); i++) {
		
			subNavi.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Object obj=e.getSource(); //클릭한 라벨을 얻어옴
					//클릭한 라벨의 인덱스와 동일한 페이지를 보여줌
					int index=subNavi.indexOf((JLabel)obj);
	
					showHide(index);				
				}
				
			});
		}
	}

	public void createNavi() {
		subNavi = new ArrayList<JLabel>(); // 배열 생성

		for (int i = 0; i < subTitle.length; i++) {
			JLabel navi = new JLabel(subTitle[i]);
			subNavi.add(navi); //리스트에 채우기
			
			navi.setPreferredSize(new Dimension(140, 45));
			p_west.add(navi);
		}
	}

	// 페이지 전환 처리
	public void showHide(int n) { // 보이게 하고 싶은 인덱스만 넘겨받음

		for (int i = 0; i < pages.length; i++) {
			if (i == n) { // 넘겨받은 매개변수와 i가 일치할때만 보이게함
				pages[i].setVisible(true);
				
				//상품 목록 페이지의 경우엔, 화면 갱신이 요구됨
				if(pages[i] instanceof ListPage) { //pages[i]의 자료형이 리스트페이지라면
					ListPage listPage= (ListPage)pages[i]; 
					listPage.getList(); //새로고침
				}
				

			} else {
				pages[i].setVisible(false);

			}
		}

	}

}
