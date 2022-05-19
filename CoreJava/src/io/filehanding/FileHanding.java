package io.filehanding;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FileHanding {

	public static void main(String[] args) throws Exception {

		//		FileProperties.setProperties();
		//
		//		FileProperties.getProperties();

		Level high = Level.HIGH;
		System.out.println(high);

		FileHandings.writeFile();

		FileHandings.readFile();

		InputStream in = FileHanding.class.getResourceAsStream("src//config.properties");

		//JSONParser jsonParser = new JSONParser();
		//JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(in, "UTF-8"));

		//System.out.println(jsonObject.toJSONString());
	}

}

class FileProperties{

	public static void setProperties() {
		try {

			Properties p=new Properties();  

			OutputStream os = new FileOutputStream("src//config.properties");

			p.setProperty("url", "jdbc:mysql//localhost:3306/?");
			p.setProperty("uName", "root");
			p.setProperty("uPass", "welcome123");

			p.store(os, "Mysql Get Connection.");

			System.out.println("store success..");

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getProperties() {
		try {

			Properties p=new Properties();  

			InputStream os = new FileInputStream("src//config.properties");

			p.load(os);

			System.out.println(p.getProperty("url"));
			System.out.println(p.getProperty("uName"));
			System.out.println(p.getProperty("uPass"));



		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}


enum Level{

	LOW,
	MEDIUM,
	HIGH
}

class FileHandings{

	public static void writeFile() {
		try {  

			FileOutputStream fos = new FileOutputStream("config.txt");

			DataOutputStream dos = new DataOutputStream(fos);

			dos.writeUTF("welcome to java");

			System.out.println("write success..");

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readFile() {
		try {

			FileInputStream fos = new FileInputStream("config.txt");

			DataInputStream dos = new DataInputStream(fos);

			String data = dos.readUTF();

			System.out.println(data);



		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class SysPro{

	public static void sysPro() {
		Properties p = System.getProperties();

		Set<String> set = p.stringPropertyNames();

		Iterator<String>  str = set.iterator();

		while (str.hasNext()) {
			String key = str.next();

			String value = p.getProperty(key);

			System.out.println(key + " : " + value);

		}
	}
}



