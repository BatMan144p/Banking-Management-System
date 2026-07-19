package bank.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bank.model.Transaction;
import bank.util.DBConnection;

public class TransactionDAO {

    // Insert Transaction
    public boolean addTransaction(Transaction transaction) {

        String sql = "INSERT INTO transaction(account_no,transaction_type,amount) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, transaction.getAccount_no());
            ps.setString(2, transaction.getTransaction_type());
            ps.setDouble(3, transaction.getAmount());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    //For transfers, we need another overloaded method.
    public boolean addTransaction(Connection con,Transaction transaction)
	throws SQLException {
	String sql =
	"INSERT INTO transaction(account_no,transaction_type,amount) VALUES(?,?,?)";
	PreparedStatement ps = con.prepareStatement(sql);
	ps.setLong(1, transaction.getAccount_no());
	ps.setString(2, transaction.getTransaction_type());
	ps.setDouble(3, transaction.getAmount());
	
	return ps.executeUpdate() > 0;
	}

    // Get Mini Statement
    public List<Transaction> getTransactions(long accountNo) {

        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transaction WHERE account_no=? ORDER BY transaction_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, accountNo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Transaction transaction = new Transaction();

                transaction.setTransaction_id(rs.getInt("transaction_id"));
                transaction.setAccount_no(rs.getLong("account_no"));
                transaction.setTransaction_type(rs.getString("transaction_type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));

                list.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
