package util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	String host = "smtp.gmail.com"; //구글의 메일 발송 서버주소
	String user = "bribrichoii@gmail.com"; //사용할 g메일 계정 (송신자)
	String password = "htvsvjziiousekaq"; //계정관리>보안>2단계인증>하단의 앱비밀번호
	Properties props = new Properties();

	public boolean send(String to, String title, String content) {
		boolean result=false; //성공여부를 알 수 있는 반환값
		
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //(수신자)
			message.setSubject(title);
			message.setContent(content, "text/html;charset=utf-8");

			Transport.send(message);
			//System.out.println("Success Message Send");
			result=true;
			
		} catch (MessagingException e) {
			e.printStackTrace();
			result=false;
		}
		return result;
	}
}