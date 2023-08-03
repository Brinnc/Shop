package org.sp.shop.network;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIServer extends JFrame{
	JPanel p_north;
	JTextField t_port; 
	JButton bt;
	
	JTextArea area;
	JScrollPane scroll;
	
	//다수의 사용자를 무한루프로 받아들일 쓰에드 생성
	//main쓰레드 보호를 위함
	Thread acceptThread;
	ServerSocket server;
	Vector<ServerMessageThread> vec;
	
	public GUIServer() {
		p_north = new JPanel();
		t_port = new JTextField("7777", 10);
		bt = new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		vec=new Vector<ServerMessageThread>();
		
		//조립 
		p_north.add(t_port);
		p_north.add(bt);
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		setBounds(500,200,300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener((e)->{
			//서버 가동하기
			
			acceptThread=new Thread() {
				public void run() {
					accessListen();
					
				}
			};
			acceptThread.start(); //쓰레드 시작하기
			bt.setEnabled(false); //버튼 비활성화, 버튼 중복해서 못누르게 처리*****
		});
	}
	
	public void accessListen() {
		int port=Integer.parseInt(t_port.getText());
		try {
			server=new ServerSocket(port);
			//무한대로 사용자의 접속을 허용
			while(true) {
				Socket socket=server.accept(); //접속자 기다리고, 접속자가 발생하면 대화형 소켓을 반환받음
				
				//접속자가 발견되면, 대화용 쓰레드를 생성하면서 소켓 등의 정보를 넘기자
				ServerMessageThread smt=new ServerMessageThread(this, socket);
				smt.start(); //대화시작
				
				vec.add(smt); //접속자 명단에 추가
				
				area.append("현재 접속자 "+vec.size()+"명\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new GUIServer();
	}
}







