package org.sp.shop.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//무한루프로 상대방의 메세지를 청취해야하므로 쓰레드를 이용
public class ClientMessageThread extends Thread{
	ClientMain clientMain;
	Socket socket;
	BufferedReader buffr; //문자 기반의 버퍼처리된 입력스트림
	BufferedWriter buffw;
	boolean flag=true; //쓰레드를 죽일지 말지 결정하는 논리값
	
	
	public ClientMessageThread(ClientMain clientMain) {
		this.clientMain=clientMain;
		this.socket=clientMain.socket;
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//듣고 
	public void listen() {
		String msg=null;
		try {
			msg=buffr.readLine();
			
			//클라이언트가 json으로 프로토콜을 정의하여 전송했으므로
			//서버에서는 이 json을 해석하여 원하는 데이터로 가공할 필요가 있음
			JSONParser jsonParser=new JSONParser();
			JSONObject jsonObject =(JSONObject)jsonParser.parse(msg);
			//00님: 메세지내용
			String id= (String)jsonObject.get("id");
			String data= (String)jsonObject.get("data");
			String message=id+"님: "+data;
			
			clientMain.area.append(message+"\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//말하기
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		while(flag) {
			listen();
		}
	
	}
}
