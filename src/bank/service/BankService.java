package bank.service;

import java.util.List;

import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.TransactionDAO;
import bank.exception.InsufficientBalanceException;
import bank.model.Account;
import bank.model.Customer;
import bank.model.Transaction;
import bank.model.TransactionType;

public class BankService {

	private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    /**
     * Registers a new customer.
     *
     * @param customer customer details to save
     * @return true if registration succeeded
     */
    public boolean registerCustomer(Customer customer) 
    {

        return customerDAO.registerCustomer(customer);

    }
    /**
     * Authenticates a customer by email and password.
     *
     * @param email    customer's email
     * @param password customer's password
     * @return the matching Customer, or null if credentials are invalid
     */
    public Customer loginCustomer(String email,String password)
    {
    	return customerDAO.loginCustomer(email,password);
    }
    //generating account number
    private long generateaccountnumber() {
    	return 1000000000L + (long)(Math.random() * 900000000);
    }
    /**
     * Creates a new account for the given customer with a generated account number.
     *
     * @param account account details (customer id, type, opening balance)
     * @return true if the account was created successfully
     */
    public boolean createAccount(Account account){
        account.setAccount_no(generateaccountnumber());
        return accountDAO.createAccount(account);
    }
    /**
     * Fetches an account and verifies it belongs to the given customer.
     * Centralizes the "does this account exist, and is it yours" check
     * that used to be duplicated across withdraw/checkBalance/transfer/miniStatement.
     *
     * @param customer  the logged-in customer
     * @param accountNo account number to validate
     * @return the Account if found and owned by the customer, otherwise null
     */
    private Account validateCustomerAccount(Customer customer, long accountNo) {
        Account account = accountDAO.getAccountByNumber(accountNo);
        if (account == null) {
            System.out.println("Account Not Found");
            return null;
        }
        if (account.getCustomer_id() != customer.getCustomer_id()) {
            System.out.println("Access Denied!");
            return null;
        }
        return account;
    }
    /**
     * Deposits money into an account and records the transaction.
     *
     * @param account_no account to deposit into
     * @param amount     amount to deposit (must be positive)
     * @return true if the deposit succeeded
     */
    public boolean deposit(long account_no,double amount) {
    	if(amount<=0) {
    		return false;
    	}
    	boolean status = accountDAO.deposit(account_no, amount);
    	if(status) {
    	    Transaction transaction = new Transaction();
    	    transaction.setAccount_no(account_no);
    	    transaction.setTransaction_type("DEPOSIT");
    	    transaction.setAmount(amount);
    	    transactionDAO.addTransaction(transaction);
    	}

    	return status;
    }
    /**
     * Withdraws money from an account after verifying ownership and balance.
     *
     * @param customer   the logged-in customer
     * @param account_no account to withdraw from
     * @param amount     amount to withdraw (must be positive)
     * @return true if the withdrawal succeeded
     * @throws InsufficientBalanceException if the account balance is too low
     */
    public boolean withdraw(Customer customer, long account_no, double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return false;
        }
 
        Account account = validateCustomerAccount(customer, account_no);
        if (account == null) {
            return false;
        }
 
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
 
        boolean status = accountDAO.withdraw(account_no, amount);
        if (status) {
            Transaction transaction = new Transaction();
            transaction.setAccount_no(account_no);
            transaction.setTransaction_type(TransactionType.WITHDRAW);
            transaction.setAmount(amount);
            transactionDAO.addTransaction(transaction);
        }
        return status;
    }
    
    /**
     * Retrieves account details, provided the account belongs to the customer.
     *
     * @param customer  the logged-in customer
     * @param account_no account to check
     * @return the Account if found and owned by the customer, otherwise null
     */
    public Account checkBalance(Customer customer, long account_no) {
        return validateCustomerAccount(customer, account_no);
    }
    /**
     * Transfers money from the customer's own account to another account.
     *
     * @param customer the logged-in customer (must own the sender account)
     * @param sender   sender account number
     * @param receiver receiver account number
     * @param amount   amount to transfer (must be positive)
     * @return true if the transfer succeeded
     * @throws InsufficientBalanceException if the sender's balance is too low
     */

    public boolean transferMoney(Customer customer, long sender, long receiver, double amount)
            throws InsufficientBalanceException {
        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return false;
        }
 
        Account senderAcc = validateCustomerAccount(customer, sender);
        if (senderAcc == null) {
            return false;
        }
 
        Account receiverAcc = accountDAO.getAccountByNumber(receiver);
        if (receiverAcc == null) {
            System.out.println("Receiver Account Not Found");
            return false;
        }
 
        if (senderAcc.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }
 
        return accountDAO.transferMoney(sender, receiver, amount);
    }
    /**
     * Retrieves the transaction history for an account, provided it belongs
     * to the customer.
     *
     * @param customer  the logged-in customer
     * @param accountNo account number
     * @return list of transactions, or null if the account is not accessible
     */
    public List<Transaction> miniStatement(Customer customer, long accountNo) {
        Account account = validateCustomerAccount(customer, accountNo);
        if (account == null) {
            return null;
        }
        return transactionDAO.getTransactions(accountNo);
    }
}