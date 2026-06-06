package testdata;

public class TransferTestData {

    // =========================================================
    // VALID TRANSFER DATA
    // =========================================================

    public static final String FROM_ACCOUNT =
            "18339";

    public static final String TO_ACCOUNT =
            "18672";

    public static final String VALID_TRANSFER_AMOUNT =
            "50";

    public static final String TRANSFER_DESCRIPTION =
            "Monthly payment";

    // =========================================================
    // INVALID TRANSFER DATA
    // =========================================================

    public static final String ZERO_AMOUNT =
            "0";

    public static final String NEGATIVE_AMOUNT =
            "-100";

    public static final String EMPTY_AMOUNT =
            "";

    public static final String EXCESSIVE_AMOUNT =
            "999999";

    public static final String INVALID_TEXT_AMOUNT =
            "abc";

    // =========================================================
    // VALIDATION MESSAGES
    // =========================================================

    public static final String SUCCESS_MESSAGE =
            "Transfer Complete!";

}