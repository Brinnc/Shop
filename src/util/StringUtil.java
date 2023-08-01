package util;

//문자열과 관련된 유용한 처리를 모아놓은 객체
public class StringUtil {
	
	//확장자 추출하기
	public static String getExt(String path) {
		//String path="나.의.중.요한 파일.jpg";
		int index=path.lastIndexOf(".");
		//System.out.println("마지막 점이 발견된 인덱스는 "+index);
		
		String ext=path.substring(index+1, path.length());
		
		return ext;
	}
	
	/*
	public static void main(String[] args) {
		System.out.println(getExt("나.의.중.요한 파일.jpg"));
		
	}
	*/
}
