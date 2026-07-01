package api.testdata;

public class ApiTestData {

    // =========================================================
    // LOGIN
    // =========================================================

    public static final String INVALID_USERNAME = "invalidUser";
    public static final String INVALID_PASSWORD = "wrongPassword";

    // =========================================================
    // ACCOUNT
    // =========================================================

    public static final String INVALID_ACCOUNT_ID = "999999999";
    public static final String CHECKING_ACCOUNT_TYPE = "0";

    // =========================================================
    // TRANSFER
    // =========================================================

    public static final String VALID_TRANSFER_AMOUNT = "2";
    public static final String ZERO_AMOUNT = "0";
    public static final String NEGATIVE_AMOUNT = "-100";

    // =========================================================
    // TRANSACTION
    // =========================================================

    public static final String RECEIVED_TRANSACTION = "Funds Transfer Received";
    public static final String SENT_TRANSACTION = "Funds Transfer Sent";

    public static final String CREDIT_TRANSACTION_TYPE = "Credit";
    public static final String DEBIT_TRANSACTION_TYPE = "Debit";

}
