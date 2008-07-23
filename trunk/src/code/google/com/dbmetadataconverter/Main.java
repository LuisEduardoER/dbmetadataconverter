package code.google.com.dbmetadataconverter;

import java.sql.SQLException;

import code.google.com.dbmetadataconverter.service.DBMetaDataConverterService;


public class Main {

	
	public static final String MY_SQL = "MySQL";
	public static final String ORACLE = "Oracle";
	public static final String MS_SQL_SERVER= "MS SQLServer";
	
	public static void main(String[] args) {
		try {
			DBMetaDataConverterService ms = new DBMetaDataConverterService(
		    		"root", "", getUrl(MY_SQL, "localhost", "test"), "com.mysql.jdbc.Driver");
			System.out.println(ms.getAllTables());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String getDriverName(String rdbms) {
		String driverName = "";
		if (rdbms.equals(MY_SQL)) {
			driverName = "com.mysql.jdbc.Driver";
		} else if (rdbms.equals(ORACLE)) {
			driverName = "oracle.jdbc.driver.OracleDriver";
		} else if (rdbms.equals(MS_SQL_SERVER)) {
			driverName = "net.sourceforge.jtds.jdbc.Driver";
		}
		return driverName;
	}
	
	public static String getUrl(String rdbms, String host, String dbName) {
		String url = "";
		if (rdbms.equalsIgnoreCase(MY_SQL)) {			
			url = "jdbc:mysql://" + host + ":3306/" + dbName;			
		} else if (rdbms.equals(ORACLE)) {
			url = "jdbc:oracle:thin:@" + host + ":1521:" + dbName;
		} else if (rdbms.equals(MS_SQL_SERVER)) {			
			url = "jdbc:jtds:sqlserver://"+host+":1433/"+dbName;
		}
		return url;
	}
}
