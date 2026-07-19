package bank.dao;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

import bank.model.Customer;
import bank.util.DBConnection;

public class CustomerDAO{
	
	//customer registration
	public boolean registerCustomer(Customer customer) {
	Connection con=null;
	try 
	{
	 con=DBConnection.getConnection();
	 String sql="INSERT into customer (name,email,phone,password) VALUES(?,?,?,?)";
	 PreparedStatement ps=con.prepareStatement(sql);
	 
	 ps.setString(1, customer.getName());
	 ps.setString(2, customer.getEmail());
	 ps.setString(3,customer.getPhone());
	 ps.setString(4,customer.getPassword());
	 
	 int rowsAffected=ps.executeUpdate();
	 return rowsAffected > 0;
	}
	catch(SQLException e) 
	{
		e.printStackTrace();
	}
	return false;
	}
	
	//customer login
	public Customer loginCustomer(String email,String password)
	{
		Connection con=null;
		String sql="SELECT * FROM customer WHERE email=? AND password=?";
		try {
			con=DBConnection.getConnection();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,email );
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				Customer customer=new Customer();
				customer.setCustomer_id(rs.getInt("customer_id"));

	            customer.setName(rs.getString("name"));

	            customer.setEmail(rs.getString("email"));

	            customer.setPhone(rs.getString("phone"));

	            customer.setPassword(rs.getString("password"));

	            return customer;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
