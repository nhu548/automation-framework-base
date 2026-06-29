package api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.math.BigDecimal;

/**
 * API client for account-related operations.
 */
public class AccountApiClient {

    /**
     * Get account details by account ID.
     *
     * @param accountId Account ID.
     * @return Raw API response.
     */
    public Response getAccountById(
            String accountId
    ) {

        return RestAssured.given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/accounts/{accountId}", accountId)
                .then()
                .extract()
                .response();
    }

    /**
     * Create a new account for a customer.
     *
     * @param customerId    Customer ID.
     * @param accountType   Account type. Example: 0 = Checking, 1 = Savings.
     * @param fromAccountId Source account ID used to fund the new account.
     * @return Raw API response.
     */
    public Response createAccount(
            String customerId,
            String accountType,
            String fromAccountId
    ) {

        return RestAssured.given()
                .log().all()
                .header("Accept", "application/xml")
                .queryParam("customerId", customerId)
                .queryParam("newAccountType", accountType)
                .queryParam("fromAccountId", fromAccountId)
                .when()
                .post("/createAccount")
                .then()
                .extract()
                .response();
    }

    /**
     * Get customer ID from account details.
     *
     * @param accountId Account ID.
     * @return Customer ID.
     */
    public String getCustomerIdByAccountId(
            String accountId
    ) {

        Response response =
                getAccountById(accountId);

        String customerId =
                response.xmlPath().getString("account.customerId");

        return customerId;
    }

    /**
     * Create a new account using an existing account as the funding account.
     *
     * @param accountId   Existing account ID.
     * @param accountType Account type. Example: 0 = Checking, 1 = Savings.
     * @return New account ID.
     */
    public String createNewAccount(
            String accountId,
            String accountType
    ) {

        String customerId =
                getCustomerIdByAccountId(
                        accountId
                );

        Response response =
                createAccount(
                        customerId,
                        accountType,
                        accountId
                );

        String newAccountId =
                response.xmlPath().getString("account.id");

        return newAccountId;
    }

    /**
     * Get account balance as BigDecimal for accurate money calculation.
     *
     * @param accountId Account ID.
     * @return Account balance.
     */
    public BigDecimal getAccountBalance(
            String accountId
    ) {

        Response response =
                getAccountById(accountId);

        String balance =
                response.xmlPath().getString("account.balance");

        if (balance == null || balance.isBlank()) {
            throw new IllegalStateException(
                    "Account balance is missing for account ID: " + accountId
            );
        }

        return new BigDecimal(balance);
    }
}