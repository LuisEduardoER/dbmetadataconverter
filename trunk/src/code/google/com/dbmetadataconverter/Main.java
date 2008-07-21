package code.google.com.dbmetadataconverter;

import java.sql.SQLException;

import code.google.com.dbmetadataconverter.service.DBMetaDataConverterService;


public class Main {

	
	
	public static void main(String[] args) {
		try {
			DBMetaDataConverterService ms = new DBMetaDataConverterService(
		    		"root", "", "localhost", DBMetaDataConverterService.MY_SQL, "test");
			ms.getAllTables();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	   
}
