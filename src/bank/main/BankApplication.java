package bank.main;

import java.util.List;
import java.util.Scanner;

import bank.exception.InsufficientBalanceException;
import bank.model.Account;
import bank.model.Customer;
import bank.model.Transaction;
import bank.service.BankService;
import bank.util.ValidationUtil;

public class BankApplication {
	private Customer loggedInCustomer;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan=new Scanner(System.in);
		BankApplication app=new BankApplication();
		
		int choice;
		
		do
		{
			System.out.println("+-----------------------------------+");
			System.out.println("+------BANK MANAGEMENT SYSTEM-------+");
			System.out.println("+-----------------------------------+");
			System.out.println("1.Customer Registration");
			System.out.println("2.Customer Login");
			System.out.println("3.Create Account");
			System.out.println("4.Deposit");
			System.out.println("5. Withdraw");
            System.out.println("6. Check Balance");
            System.out.println("7. Transfer Money");
            System.out.println("8. Mini Statement");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice=scan.nextInt();
            scan.nextLine();
          
            switch(choice) {
            
            case 1:
            	
			        app.registerCustomer(scan);
		            break;
            case 2:
                	app.loginCustomer(scan);
                    break;
            case 3:
            		app.createAccount(scan);
                    break;
            case 4:
            		app.deposit(scan);
                    break;
            case 5:
            		app.withdraw(scan);
                break;
            case 6:
                app.checkBalance(scan);
                break;
            case 7:
                app.transferMoney(scan);
                break;
            case 8:
                app.miniStatement(scan);
                break;
            case 9:
                System.out.println("Thank you for using our Banking System!");
                break;

            default:

                System.out.println("Invalid Choice!");
            }
		}while(choice!=9);
		
		scan.close();
	}
		public void registerCustomer(Scanner scan) {
			System.out.println("------Customer Registration-----");
			//name 
			String name;
			do {
				System.out.println("Enter the name =");
				name=scan.nextLine();
				if(!ValidationUtil.isValidName(name))
					System.out.println("Invalid Name! Only alphabets and spaces (3-50 characters).");
			}while(!ValidationUtil.isValidName(name));
			//email
			String email;
			do {
			System.out.print("Enter the email =");
			email=scan.nextLine();
			if(!ValidationUtil.isValidEmail(email))
				System.out.println("Invalid Email");
			}while(!ValidationUtil.isValidEmail(email));
			//phone
			String phone;
			do {
			System.out.print("Enter the phone =");
			phone=scan.nextLine();
			if(!ValidationUtil.isValidPhone(phone))
				System.out.println("Invalid phone -enter only 10 digit number");
			}while(!ValidationUtil.isValidPhone(phone));
			//password
			String password;
			do {
			System.out.print("Enter the password =");
			password=scan.nextLine();
			if(!ValidationUtil.isValidPassword(password)) {
				System.out.println("Password must contain:");
			        System.out.println("- Minimum 8 characters");
			        System.out.println("- One uppercase letter");
			        System.out.println("- One lowercase letter");
			        System.out.println("- One digit");
			}	
			}while(!ValidationUtil.isValidPassword(password));
			Customer customer=new Customer();
			customer.setName(name);
			customer.setEmail(email);
			customer.setPhone(phone);
			customer.setPassword(password);
			BankService service=new BankService();
			boolean status=service.registerCustomer(customer);
			if(status) {
				System.out.println("registration successful");
			}
			else {
				System.out.println("registration failed");
			}
	}
		public void loginCustomer(Scanner scan) {
			 System.out.println("\n------ Customer Login ------");
			 System.out.println("Enter your email");
			 String email=scan.nextLine();
			 System.out.println("Enter your password");
			 String password=scan.nextLine();
			 BankService service=new BankService();
			 Customer customer=service.loginCustomer(email,password);
			 if(customer!=null) {
				 loggedInCustomer=customer;
				 System.out.println("Login successful");
			 	 System.out.println("Welcome"+customer.getName());
			 }
			 else
				 System.out.println("Invalid email password");
			
		}
		public boolean isLoggedIn(){

		    return loggedInCustomer != null;

		}
		public void createAccount(Scanner scan) {
			if(!isLoggedIn()) {
		        System.out.println("Please login first.");
		        return;
		    }
			System.out.println("\n------ Create Account ------");
			System.out.println("enter acc type (Savings/Current)");
			String account_type=scan.nextLine();
			System.out.println("enter opening balance");
			double balance=scan.nextDouble();
			scan.nextLine();
			Account account=new Account();
			account.setCustomer_id(loggedInCustomer.getCustomer_id());
			account.setAccount_type(account_type);
			account.setBalance(balance);

		    BankService service = new BankService();

		    boolean status = service.createAccount(account);

		    if(status)

		        System.out.println("Account Created Successfully.");

		    else

		        System.out.println("Account Creation Failed.");
		}
		
		//deposit
		public void deposit(Scanner scan) {
			if(!isLoggedIn()) {
				System.out.println("Please login first");
				return;
			}
			System.out.println("Deposit Money");
			System.out.println("Enter the account no");
			long account_no=scan.nextLong();
			System.out.println("Enter Deposit amount");
			double amount=scan.nextDouble();
			scan.nextLine();
			BankService service=new BankService();
			boolean status=service.deposit(account_no,amount);
			if(status)
				System.out.println("Deposit successful");
			else
				System.out.println("deposit failed");
			
			
		}
		//withdraw money
		public void withdraw(Scanner scan) {
			if(!isLoggedIn()) {
				System.out.println("please login first");
				return;
			}
			System.out.println("Withdraw Money");
			System.out.println("Enter the account number");
			Long account_no=scan.nextLong();
			System.out.println("enter the amount to withdraw");
			double amount=scan.nextDouble();
			BankService service=new BankService();
			try {
				boolean status=service.withdraw(loggedInCustomer,account_no,amount);
				if(status) {
					System.out.println("Withdraw successful");
				}
				else {
					System.out.println("Withdraw failed");
				}
			} catch (InsufficientBalanceException e) {
				// TODO Auto-generated catch block
				
				e.getMessage();
			}
			
			
		}
		//checking the balance
		public void checkBalance(Scanner scan) {
			if(!isLoggedIn()) {
				System.out.println("Please Login First.");
				return;
			}
			 System.out.println("\n------ Check Balance ------");
			 System.out.print("Enter Account Number : ");
			long account_no=scan.nextLong();
			scan.nextLine();
			BankService service=new BankService();
			Account account=service.checkBalance(loggedInCustomer, account_no);
			if(account!=null) {
				 System.out.println("--------------------------------");
				 System.out.println("Account number="+account.getAccount_no());
				 System.out.println("Account type="+account.getAccount_type());
				 System.out.println(" Account Balance="+account.getBalance());
			}
			else {
				System.out.println("Unable to Fetch Balance.");
			}
			
		}
		//Transfer money
		public void transferMoney(Scanner scan) {
			if(!isLoggedIn()) {
				System.out.println("Login first");
				return;
			}
			System.out.println("\n------ Transfer Money ------");
			System.out.println("Enter your account number");
			long sender=scan.nextLong();
			System.out.println("Enter receivers account number");
			long receiver=scan.nextLong();
			System.out.println("Enter the amount amount");
			double amount=scan.nextDouble();
			scan.nextLine();
			BankService service=new BankService();
			boolean status;
			try {
				status = service.transferMoney(loggedInCustomer,sender,receiver,amount);
				if(status) 
					System.out.println("Transfer Successful.");
				    else
				    	System.out.println("Transfer Failed.");
			} catch (InsufficientBalanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//mini statement
		public void miniStatement(Scanner scan) {
		    if(!isLoggedIn()) {
		        System.out.println("Please Login First.");
		        return;
		    }
		    System.out.println("\n------ MINI STATEMENT ------");
		    System.out.print("Enter Account Number : ");
		    long accountNo = scan.nextLong();
		    scan.nextLine();
		    BankService service=new BankService();
		    List<Transaction> transactions =service.miniStatement(loggedInCustomer,accountNo);
		    if(transactions == null || transactions.isEmpty()) {
		        System.out.println("No Transactions Found.");
		        return;
		    }
		    System.out.println("--------------------------------------------------------------");
		    System.out.printf("%-5s %-12s %-12s %-15s%n",
		            "ID","TYPE","AMOUNT","DATE");
		    System.out.println("--------------------------------------------------------------");
		    for(Transaction t : transactions) {
		        System.out.printf("%-5d %-12s %-12.2f %-15s%n",
		                t.getTransaction_id(),
		                t.getTransaction_type(),
		                t.getAmount(),
		                t.getTransaction_date());
		    }
		    System.out.println("--------------------------------------------------------------");
		}
		
}

