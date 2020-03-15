package com.CountySocialMedia.dbApi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
public class DbConnection {
	static InputStream input;
	static Connection con=null;
	static Properties prop ;
	public static  Connection createDbConnection() {
		try {
			//"resources/configProperties/dbCredentials.properties"
			//System.getProperty("catalina.base")+"/webapps/CountySocialMedia/WEB-INF/classes/configProperties/dbCredentials.properties"
			
			
			  input=new FileInputStream(System.getProperty("catalina.base")+
			  "/webapps/CountySocialMedia/WEB-INF/classes/configProperties/dbCredentials.properties"
			  ); 
			  prop = new Properties(); prop.load(input); 
			  String dbDriver=prop.getProperty("dbDriver"); 
			  String  dbUrl=prop.getProperty("dbUrl"); 
			  String Uname=prop.getProperty("dbUname");
			  String pass=prop.getProperty("dbPass");
			 
			 Class.forName(dbDriver); 
			 con=DriverManager.getConnection(dbUrl, Uname,pass); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
