package org.sp.shop.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdminMain extends JFrame {
	JPanel p_north;
	String[] naviTitle = { "상품관리", "회원관리", "주문관리", "게시판관리" };
	ArrayList<JLabel> navi;
	JLabel la_login; // 로그인 상태 출력라벨
	JPanel p_center; // 각 컨텐츠 페이지들이 들어올 빈 영역

	LoginForm loginForm;

	// 각 페이지의 index가 직관성이 없기 때문에 상수로 표현함
	public static final int PRODUCT = 0;
	public static final int MEMBER = 1;
	public static final int ORDER = 2;
	public static final int BLOG = 3;

	// 컨텐츠 페이지
	Page[] pages;

	public AdminMain() {
		p_north = new JPanel();
		createNavi();
		la_login = new JLabel();
		p_center = new JPanel();
		pages = new Page[4];

		// 페이지 생성
		pages[PRODUCT] = new ProductPage();
		pages[MEMBER] = new MemberPage();
		pages[ORDER] = new OrderPage();
		pages[BLOG] = new BlogPage();

		// 스타일
		p_center.setBackground(Color.GRAY);

		// 조립
		p_north.add(la_login);
		for (int i = 0; i < pages.length; i++) {
			p_center.add(pages[i]);
		}
		add(p_north, BorderLayout.NORTH);
		add(p_center);

		setSize(1100, 600);
		// setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 로그인폼 생성
		loginForm = new LoginForm(this);

		// 최초로 상품 페이지는 보여지게 처리함
		showHide(PRODUCT);

		// 네비게이션에 대한 이벤트 연결
		for (int i = 0; i < navi.size(); i++) {
			JLabel obj=navi.get(i);
			
			obj.addMouseListener(new MouseAdapter() {
				// 마우스를 올려놓으면, 배경색을 적용해 hover 효과처리
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel la= (JLabel)e.getSource();
					la.setBackground(Color.BLACK);
					la.setForeground(Color.WHITE);
				}

				// 마우스를 내려놓으면
				@Override
				public void mouseExited(MouseEvent e) {
					JLabel la= (JLabel)e.getSource();
					la.setBackground(null);
					la.setForeground(Color.BLACK);
				}

				// 클릭 시 해당 페이지
				@Override
				public void mouseClicked(MouseEvent e) {
					int index=navi.indexOf(e.getSource()); //이벤트를 일으킨 JLabel이 몇번째인지
					showHide(index);

				}
			});
		}

		// 로그아웃 처리
		la_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(AdminMain.this, "로그아웃 하시겠습니까?");
				if (result == JOptionPane.OK_OPTION) {
					logout();
				}

			}
		});
	}

	public void createNavi() {
		navi = new ArrayList<JLabel>();
		for (int i = 0; i < naviTitle.length; i++) {
			JLabel obj = new JLabel(naviTitle[i]); // 네비 생성
			navi.add(obj); // 리스트에 추가
			p_north.add(obj); // 북쪽 패널에 네비 부착

		}

	}

	// 관리자 로그인 시 호출될 메서드
	public void login() {
		la_login.setText("로그아웃");
		// 프레임의 상단 제목에 로그인 관리자 이름 출력
		this.setTitle(loginForm.adminDTO.getName() + " 님 로그인 중");

		// 현재 메인프레임을 보이게 처리함
		this.setVisible(true);
		// 로그인폼 안보이게 처리함
		loginForm.setVisible(false);
	}

	// 관리자 로그아웃 시 호출될 메서드
	public void logout() {
		la_login.setText("로그인");
		this.setTitle("");

		// 관리자 정보가 들어있던 DTO 다시 초기화
		loginForm.adminDTO = null;
		// 현재 메인프레임을 다시 안보이게 처리함
		this.setVisible(false);
		// 로그인폼 등장
		loginForm.setVisible(true);
	}

	// 페이지 전환 처리
	public void showHide(int n) { // 보이게 하고 싶은 인덱스만 넘겨받음

		for (int i = 0; i < pages.length; i++) {
			if (i == n) { // 넘겨받은 매개변수와 i가 일치할때만 보이게함
				pages[i].setVisible(true);

			} else {
				pages[i].setVisible(false);

			}
		}

	}

	public static void main(String[] args) {
		System.out.println("실행");
		new AdminMain();
	}
}
