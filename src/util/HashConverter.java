package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//특정 데이터에 대해 즉, 어떤 종류의 길이를 가진 데이터라 할지라도
//해시 함수를 이용하면, 동일한 길이의 문자열을 반환해줌
//유저가 입력한 패스워드를 동일한 길이의 암호문자열로 변환할 때도 유용함
//이미 java는 Hash알고리즘을 처리할 수 있는 클래스가 지원됨
public class HashConverter {

	public String convertToHash(String password) {
		//String password = "minzino";

		/*
		 * for(int i=0; i<b.length; i++) { System.out.println((char)b[i]); }
		 */

		// Hash알고리즘으로 처리
		StringBuilder str=new StringBuilder();
		
		try {
			byte[] b = password.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			byte[] hash = md.digest(b); // 쪼개진 바이트 데이터를 대상으로 알고리즘을 적용

			
			for (int i = 0; i < hash.length; i++) {
				//System.out.println(hash[i]);
				String hex=Integer.toHexString(0xff & hash[i]);
				System.out.println(hex);
				if(hex.length()==1)str.append("0");	
				str.append(hex);
			}
			System.out.println(str.toString()); //해시값
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return str.toString();
	}

}
