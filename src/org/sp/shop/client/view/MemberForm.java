package org.sp.shop.client.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.sp.shop.admin.domain.Member;
import org.sp.shop.client.model.MemberDAO;

import util.DBManager;
import util.HashConverter;
import util.MailSender;


public class MemberForm extends JFrame{
	JTextField t_id;
	JPasswordField t_pass;
	JTextField t_name;
	JTextField t_email;
	JButton bt;
	
	DBManager dbManager;
	MemberDAO memberDAO;
	HashConverter hashConverter;
	MailSender mailSender;
	
	public MemberForm() {
		t_id=new JTextField();
		t_pass=new JPasswordField();
		t_name=new JTextField();
		t_email=new JTextField();
		bt=new JButton("가입");
		
		dbManager=new DBManager();
		memberDAO=new MemberDAO(dbManager);
		hashConverter=new HashConverter();
		mailSender=new MailSender();
		
		this.setLayout(new FlowLayout());
		
		//스타일
		Dimension d=new Dimension(280, 35);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_email.setPreferredSize(d);
		
		add(t_id);
		add(t_pass);
		add(t_name);
		add(t_email);
		add(bt);
		
		setSize(300, 250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener((e)->{
			regist();
		});
		
	}
	
	public void regist() {
		//1) db에 넣기
		//사용자가 입력한 입력폼의 내용을 1개의 dto에 담아서 insert메서드 호출
		Member member=new Member();
		member.setId(t_id.getText()); //아이디대입
		member.setPass(hashConverter.convertToHash(new String(t_pass.getPassword())));
		member.setName(t_name.getText());
		member.setEmail(t_email.getText());
		
		int result=memberDAO.insert(member); //insert호출
		
		if(result>0) {
			//2) 이메일 발송
			boolean flag=mailSender.send(member.getEmail(), "가입추카", member.getName()+"님 테스트테스트임");
			if(flag) {
				JOptionPane.showMessageDialog(this, "가입완료");
			}
		}
	}
	
	public static void main(String[] args) {
		new MemberForm();
	}
}
