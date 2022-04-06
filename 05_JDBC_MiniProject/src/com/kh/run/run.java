package com.kh.run;

import java.io.File;
import java.io.FileInputStream;

import java.util.Properties;

import com.kh.view.ProductView;

public class run {

	public static void main(String[] args) {
		
		new ProductView().mainView();
		
//		File f = new File("resources");
//		f.mkdir();
//		
//		
		Properties prop = new Properties();
//		
//		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
//		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
//		prop.setProperty("username", "JDBC");
//		prop.setProperty("password", "JDBC");
//		
//		try {
//		prop.store(new FileOutputStream("resources/driver.properties"), "driver properties");
//		prop.storeToXML(new FileOutputStream("resources/driver.xml"), "driver xml");
//		
//		
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	try {
//		prop.load(new FileInputStream("resources/driver.properties"));
//		System.out.println(prop.getProperty("driver"));
//		System.out.println(prop.getProperty("url"));
//		System.out.println(prop.getProperty("username"));
//		System.out.println(prop.getProperty("password"));
//		
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	}

}
