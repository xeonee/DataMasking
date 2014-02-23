package com.dataMasking;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MaskData {
	private transient final static Log logger = LogFactory.getLog(MaskData.class.getName());

	public static void createQuery(Connection con, String tableName, String primaryKey, List columnNames, String schema) {

		String query = "SELECT "+primaryKey+", "+createQuery(columnNames)+" FROM "+schema+"."+tableName;
		try{
			String columnName="";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String primaryKeyValue = rs.getString(primaryKey);
				for (Iterator iterator = columnNames.iterator(); iterator.hasNext();) {
					columnName = (String) iterator.next();
					String columnNameValue = rs.getString(columnName);
					String encryptedvalue = CaesarCipher.encryptMessage(columnNameValue);
					Update(con, tableName, schema, columnName, encryptedvalue, primaryKey, primaryKeyValue);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void Update(Connection con, String tableName,String schema, String columnName, String encryptedvalue, String primaryKey, String primaryKeyValue) 
	throws SQLException {
		PreparedStatement ps = null;
		String query = "UPDATE "+schema+"."+tableName+" SET "+columnName+" = ? "+" WHERE "+primaryKey+" = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, encryptedvalue);
			ps.setString(2, primaryKeyValue);
			ps.executeUpdate();
		}
		finally {
			if (ps != null) ps.close();
		}
	}

	private static String createQuery(List columnList){
		StringBuilder strbuffer = new StringBuilder();
		Iterator itr = columnList.iterator();
		strbuffer.append(itr.next());
		while(itr.hasNext())
			strbuffer.append(", "+itr.next());
		return strbuffer.toString();
	}
}
