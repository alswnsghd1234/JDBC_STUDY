package com.kh.run;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.kh.view.MemberView;

public class Run {

	public static void main(String[] args) {
		
		
		/*
		 * Properties : Map계열의 컬렉션, key+value 세트로 담는게 특징
		 * Properties는 주로 외부 설정파일을 읽어오기 또는 파일형태로 출력하고자 할 때 사용한다.
		 * 
		 * properties,xml 파일로 내보내기 -> store(), storeToXML()
		 * 
		 * */
		
//		File f = new File("resources");
//		f.mkdir(); 폴더생성
//		
		Properties prop = new Properties();
//		
//		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
//		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
//		prop.setProperty("username", "JDBC");
//		prop.setProperty("password", "JDBC");
//		
//		try {
//			prop.store(new FileOutputStream("resources/driver.properties"), "driver properties");
//			prop.storeToXML(new FileOutputStream("resources/driver.xml"), "driver xml");
//			
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			prop.load(new FileInputStream("resources/driver.properties"));
//			System.out.println(prop.getProperty("driver"));
//			System.out.println(prop.getProperty("url"));
//			System.out.println(prop.getProperty("username"));
//			System.out.println(prop.getProperty("password"));
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		new MemberView().mainMenu();
	}

}
