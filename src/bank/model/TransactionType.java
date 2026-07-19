package bank.model;

/**
 * Central place for transaction type labels.
 * Prevents typos like "TRANSFER OUT" vs "TRANSFER-OUT" from creeping
 * into different parts of the codebase.
 */
public class TransactionType {
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    public static final String TRANSFER_OUT = "TRANSFER OUT";
    public static final String TRANSFER_IN = "TRANSFER IN";
 
    private TransactionType() {
        // utility class, no instances needed
    }
}
 