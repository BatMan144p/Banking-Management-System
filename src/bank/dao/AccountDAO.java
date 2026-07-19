package bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.model.Account;
import bank.model.Transaction;
import bank.util.DBConnection;

public class AccountDAO {
	public boolean createAccount(Account account) {
		String sql="INSERT INTO account (account_no,customer_id, account_type, balance)VALUES(?,?,?,?) ";
		Connection con=null;
		try {
			con=DBConnection.getConnection();
			PreparedStatement st=con.prepareStatement(sql);
			st.setLong(1, account.getAccount_no());
			st.setInt(2, account.getCustomer_id());
			st.setString(3, account.getAccount_type());
			st.setDouble(4, account.getBalance());
			int rows=st.executeUpdate();
			return rows>0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deposit(long account_no,double amount) {
		
		String sql="UPDATE account SET balance=balance+? WHERE account_no=?";
		try (Connection con = DBConnection.getConnection();
		     PreparedStatement st = con.prepareStatement(sql)) {
			st.setDouble(1, amount);
			st.setLong(2,account_no);
			int rows=st.executeUpdate();
			return rows>0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
//	//cheking account exsists
//	public boolean accountExsists(long account_no) {
//		String sql="SELECT * FROM account WHERE account_no=?";
//		try(Connection con=DBConnection.getConnection();
//				PreparedStatement st=con.prepareStatement(sql);) {
//			st.setLong(1, account_no);
//			ResultSet rs=st.executeQuery();
//			return rs.next();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
//	//getting the account balance
//	public double getBalance(long account_no) {
//		String sql="SELECT balance FROM account WHERE account_no=?";
//		try(Connection con=DBConnection.getConnection();
//				PreparedStatement st=con.prepareStatement(sql);){
//				st.setLong(1, account_no);
//				ResultSet rs=st.executeQuery();
//				if(rs.next()) {
//					return rs.getDouble("balance");
//				}	
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return -1;
//		
//	}
	//// Get complete account details
	public Account getAccountByNumber(long account_no) {
		String sql="SELECT * FROM account WHERE account_no=?";
		try(Connection con=DBConnection.getConnection();
				PreparedStatement st=con.prepareStatement(sql);){
			st.setLong(1, account_no);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Account account=new Account();
				account.setAccount_no(rs.getLong("account_no"));
				account.setCustomer_id(rs.getInt("customer_id"));
				account.setAccount_type(rs.getString("account_type"));
				account.setBalance(rs.getDouble("balance"));
				return account;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//withdrawing amount from account
	public boolean withdraw(long account_no,double amount) {
		String sql="UPDATE account SET balance=balance-? WHERE account_no=?";
		try(Connection con=DBConnection.getConnection();
				PreparedStatement st=con.prepareStatement(sql);){
			st.setDouble(1, amount);
			st.setLong(2, account_no);
			int rowsAffected=st.executeUpdate();
			return rowsAffected>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//overloading deposit and withdraw methods for transfering money
	public boolean withdraw(Connection con,long account_no,double amount) {
		String str="UPDATE account SET balance=balance-? WHERE account_no=?";
		try(PreparedStatement st=con.prepareStatement(str)) {
			st.setDouble(1, amount);
			st.setLong(2, account_no);
			
			return st.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean deposit(Connection con,long account_no,double amount) {
		String str="UPDATE account SET balance=balance+? WHERE account_no=?";
		try(PreparedStatement st=con.prepareStatement(str)){
			st.setDouble(1, amount);
			st.setLong(2, account_no);
			return st.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//transfer money
	public boolean transferMoney(long sender,long receiver,double amount) {
		Connection con=null;
		TransactionDAO transactionDAO = new TransactionDAO();
		try {
			con=DBConnection.getConnection();
			con.setAutoCommit(false);
			boolean withdrawStatus=withdraw(con,sender,amount);
			if(!withdrawStatus) {
				con.rollback();
				return false;
			}
			boolean depositStatus=deposit(con,receiver,amount);
			if(!depositStatus) {
				con.rollback();
				return false;
			}
			// Sender Transaction
	        Transaction senderTransaction = new Transaction();
	        senderTransaction.setAccount_no(sender);
	        senderTransaction.setTransaction_type("TRANSFER OUT");
	        senderTransaction.setAmount(amount);
	        boolean senderHistory =transactionDAO.addTransaction(con,senderTransaction);
	        if (!senderHistory) {
	            con.rollback();
	            return false;
	        }
	     // Receiver Transaction
	        Transaction receiverTransaction =new Transaction();
	        receiverTransaction.setAccount_no(receiver);
	        receiverTransaction.setTransaction_type("TRANSFER IN");
	        receiverTransaction.setAmount(amount);
	        boolean receiverHistory =transactionDAO.addTransaction(con,receiverTransaction);
	        if (!receiverHistory) {
	            con.rollback();
	            return false;
	        }
			//everything successful
			con.commit();
			return true;
		}
		catch (SQLException e) {
	        try {
	            if (con != null)
	                con.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (con != null) {
	                con.setAutoCommit(true);
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
return false;
	}
}
