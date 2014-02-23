package com.dataMasking;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;

public class ViewStorageMap {
	private transient final static Log logger = LogFactory.getLog(ViewStorageMap.class.getName());

	public static void main(String args[]) {
	
		try {
			logger.info("Data Masking Process started at "+Calendar.getInstance().getTime());
			Connection con = ReadDBDetails.readPropertiesFile();
			long startTime = System.currentTimeMillis();
			long endTime;

			Mapping mapping = new Mapping();
			mapping.loadMapping("mapping.xml");

			Unmarshaller un = new Unmarshaller(StorageMap.class);
			un.setMapping(mapping);

			FileReader fr = new FileReader("TablesAndColumns.xml");
			StorageMap storageMap = (StorageMap) un.unmarshal(fr);
			fr.close();

			List tables = storageMap.getTable();
			Iterator itr = tables.iterator();
			while(itr.hasNext()){
				Table table = (Table) itr.next();
				List columns = table.getColumn();
//				Iterator itrClm = columns.iterator();
//				while(itrClm.hasNext()){
					long sTime = System.currentTimeMillis();
					MaskData.createQuery(con, table.getName(), table.getPkid(), columns, table.getSchema());
					long eTime = System.currentTimeMillis();
					logger.info("it took "+(eTime-sTime)+" milli-seconds for table "+table.getName());
//				}
				logger.info("Process completed for table "+table.getName());
			}
			endTime = System.currentTimeMillis();
			logger.info(getElapsedTime(startTime, endTime));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MappingException e) {
			e.printStackTrace();
		}
	}

	public static String getElapsedTime(long startTime, long endTime){
		String hours, minutes, seconds;
		long elapsedTime = (endTime - startTime)/1000;

		seconds = Integer.toString((int)(elapsedTime % 60));
		minutes = Integer.toString((int)((elapsedTime % 3600) / 60));
		hours = Integer.toString((int)(elapsedTime / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		return ("Data Masking Process completed in "+hours+" Hours "+minutes+" Minutes "+seconds+" Seconds");
	}
}