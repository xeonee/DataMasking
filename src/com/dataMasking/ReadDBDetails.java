package com.dataMasking;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import org.apache.commons.logging.*;

public class ReadDBDetails {

	public static final String DBDETAILS_PROPERTY_FILE_NAME = "DBDetails.properties";
	private transient final static Log logger = LogFactory.getLog(ReadDBDetails.class.getName());

	static String dbdriver_class;
	static String db_url;
	static String db_username;
	static String db_password;
	static Connection con = null;

	public static Connection readPropertiesFile(){
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(DBDETAILS_PROPERTY_FILE_NAME);
			prop.load(fis);

			dbdriver_class = prop.getProperty("dbdriver_class");
			db_url = prop.getProperty("db_url");
			db_username = prop.getProperty("db_username");
			db_password = prop.getProperty("db_password");

			logger.info("DB Driver Class: "+dbdriver_class);
			logger.info("DB URL: "+db_url);
			logger.info("DB User Name: "+db_username);
			logger.info("DB Password: "+db_password);

			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Class.forName(dbdriver_class);			
		} catch(java.lang.ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(db_url, db_username, db_password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
