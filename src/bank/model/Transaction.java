package bank.model;

import java.sql.Timestamp;

public class Transaction {
	private int transaction_id;
	private long account_no;
	private String transaction_type;
	private double amount;
	private Timestamp transaction_date;
	
	public Transaction() {}

	public Transaction(int transaction_id, long account_no, String transaction_type, double amount,
			Timestamp transaction_date) {
		super();
		this.transaction_id = transaction_id;
		this.account_no = account_no;
		this.transaction_type = transaction_type;
		this.amount = amount;
		this.transaction_date = transaction_date;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public long getAccount_no() {
		return account_no;
	}

	public void setAccount_no(long account_no) {
		this.account_no = account_no;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Timestamp transaction_date) {
		this.transaction_date = transaction_date;
	}

	@Override
	public String toString() {
		return "Transaction [transaction_id=" + transaction_id + ", account_no=" + account_no + ", transaction_type="
				+ transaction_type + ", amount=" + amount + ", transaction_date=" + transaction_date + "]";
	}
	
	
}
