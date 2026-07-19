package bank.model;

public class Account {
	private long account_no;
	private int customer_id;
	private String account_type;
	private double balance;
	public Account(){}
	public Account(long account_no, int customer_id, String account_type, double balance) {
		super();
		this.account_no = account_no;
		this.customer_id = customer_id;
		this.account_type = account_type;
		this.balance = balance;
	}
	public long getAccount_no() {
		return account_no;
	}
	public void setAccount_no(long account_no) {
		this.account_no = account_no;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account [account_no=" + account_no + ", customer_id=" + customer_id + ", account_type=" + account_type
				+ ", balance=" + balance + "]";
	}
	
	
	
}
