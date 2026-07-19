package bank.util;
import java.sql.*;
public class DBConnection {
	
	static {
	try {
		//load and register the driver
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	 
	//Establish the connection
	public static Connection getConnection() throws SQLException
	{
		String url="jdbc:mysql://127.0.0.1:3306/bankdb";
		String user="root";
		String pass="Lokesh@123";
		return DriverManager.getConnection(url,user,pass);
	}
	
	public static void closeConnection(Connection con,PreparedStatement ps)
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
