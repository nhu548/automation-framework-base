package api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;

/**
 * API client for transaction-related operations.
 */
public class TransactionApiClient {

    /**
     * Get all transactions for an account
     * @param accountId Account ID
     * @return Raw API response
     */
    public Response getTransactionsByAccountId(String accountId) {

        return RestAssured.given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/accounts/{accountId}/transactions", accountId)
                .then()
                .extract()
                .response();
    }

    /**
     * Get transaction IDs from an account transaction list
     * @param accountId Account ID
     * @return List of transaction ID
     */
    public List<String> getTransactionIdsByAccountId(String accountId) {

        Response response = getTransactionsByAccountId(accountId);

        return response.xmlPath().getList(
                "transactions.transaction.id"
        );
    }

    /**
     * Find the newly created transaction ID by comparing
     * transaction IDs before and after an action.
     *
     * @param transactionIdsBefore Transaction IDs before action.
     * @param transactionIdsAfter  Transaction IDs after action.
     * @return Newly created transaction ID.
     */
    public String findNewTransactionId(
            List<String> transactionIdsBefore,
            List<String> transactionIdsAfter
    ) {

        for (String transactionId : transactionIdsAfter) {
            if (!transactionIdsBefore.contains(transactionId)) {
                return transactionId;
            }
        }

        throw new IllegalStateException(
                "No new transaction ID was found after the action."
        );
    }

    /**
     * Get transaction details by transaction ID.
     *
     * @param transactionId Transaction ID.
     * @return Raw API response.
     */
    public Response getTransactionById(String transactionId) {

        return RestAssured.given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/transactions/{transactionId}", transactionId)
                .then()
                .extract()
                .response();
    }


}
