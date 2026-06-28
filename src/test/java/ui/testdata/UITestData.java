package ui.testdata;

public class UITestData {

    // =========================================================
    // VALID TRANSFER DATA
    // =========================================================

    public static final String FROM_ACCOUNT =
            "13788";

    public static final String TO_ACCOUNT =
            "15120";

    public static final String VALID_TRANSFER_AMOUNT =
            "20";

    public static final String TRANSFER_DESCRIPTION =
            "Monthly payment";

    public static final String CHECKING_ACCOUNT_TYPE =
            "CHECKING";

    public static final String ACCOUNT_SAVING_TYPE =
            "SAVINGS";


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

    public static final String INVALID_USERNAME =
            "invalidUser";

    public static final String INVALID_PASSWORD =
            "wrongPassword";

    // =========================================================
    // VALIDATION MESSAGES
    // =========================================================

    public static final String SUCCESS_MESSAGE =
            "Transfer Complete!";

}