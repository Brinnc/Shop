package org.sp.shop.admin.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.sp.shop.admin.domain.Admin;
import org.sp.shop.admin.model.AdminDAO;

import util.DBManager;

public class LoginForm extends JFrame{
	AdminMain adminMain;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt;
	Admin adminDTO;
	
	//DAO를 이용하여 DB업무를 수행하므로, DAO를 보유
	AdminDAO adminDAO;
	
	DBManager dbManager;
	
	public LoginForm(AdminMain adminMain) {
		super("관리자 로그인");
		this.adminMain=adminMain; //메인 프레임 넘겨받기 (: 생성자 주입)
		
		t_id=new JTextField();
		t_pass=new JPasswordField();
		bt=new JButton("LOGIN");
		dbManager=new DBManager();
		adminDAO=new AdminDAO(dbManager);
		
		//스타일
		Dimension d=new Dimension(360, 45);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		
		setLayout(new FlowLayout());
		
		//조립
		add(t_id);
		add(t_pass);
		add(bt);
		
		setSize(400, 220);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener((e)->{
			//재정의할 메서드가 1개뿐일 경우 굳이 클래스까지 거창하게 갈 필요는 없음
			//이때는 함수기반의 코드 작성방법으로 알려진 Lambda 표현식으로 진행
			loginCheck();
		});
	}
	
	//로그인이 성공되면, 관리자 메인 프레임을 보이게 처리하자
	public void loginCheck() {
		//사용자가 입력한 아이디와 패스워드를 채워넣을 빈(empty) 상태의 DTO생성
		String id=t_id.getText(); //사용자 입력 ID
		String pass=new String(t_pass.getPassword());
		
		Admin admin=new Admin();
		admin.setId(id); //ID대입
		admin.setPass(pass); //비번대입
		
		//login() 호출 후 반환되는 DTO의 값이 null이 아니라면, 로그인 성공으로 판단함
		adminDTO=adminDAO.login(admin);
		
		if(adminDTO==null) { //로그인 실패
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않음");
		}else { //로그인 성공
			JOptionPane.showMessageDialog(this, "로그인 성공");
			adminMain.login();
			
			/* -> 메인프레임으로 이동
			//성공 시 메인 프레임 보여지게 함
			adminMain.setVisible(true);
			//나(로그인폼)는 안보이게 함
			this.setVisible(false);
			*/
		}
	}
}
