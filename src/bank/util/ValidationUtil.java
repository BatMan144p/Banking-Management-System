package bank.util;

import java.util.regex.Pattern;

public class ValidationUtil {
	//Name validation
	public static boolean isValidName(String name) {
		return name!=null && 
				name.matches("[A-Za-z ]{3,50}");
	}
	//Email Validation
	public static boolean isValidEmail(String email) {
		return email!=null && 
				Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$",email);
	}
	//Phone Validation
	public static boolean isValidPhone(String phone) {
		return phone!=null && 
				phone.matches("\\d{10}");		
	}
	//Password Validation
	public static boolean isValidPassword(String password) {
		if(password==null)
			return false;
		return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$");
	}
	//Amount Validation
	public static boolean isValidAmount(double amount) {
		return amount > 0;
	}
}
