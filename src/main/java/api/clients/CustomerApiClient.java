package api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

/**
 * API client for customer-related operations.
 */
public class CustomerApiClient {

    /**
     * Get customer details by customer ID.
     */
    public Response getCustomerById(
            String customerId
    ) {
        return RestAssured.given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/customers/{customerId}", customerId)
                .then()
                .extract()
                .response();
    }

    /**
     * Get all accounts that belong to a customer.
     */
    public Response getAccountsByCustomerId(
            String customerId
    ) {

        return RestAssured.given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/customers/{customerId}/accounts", customerId)
                .then()
                .extract()
                .response();
    }

    /**
     * Return the newest account ID of a customer.
     */
    public String getLatestAccountIdByCustomerId(
            String customerId
    ) {

        Response response =
                getAccountsByCustomerId(customerId);

        List<String> accountIds =
                response.xmlPath()
                        .getList("accounts.account.id");

        if (accountIds == null || accountIds.isEmpty()) {
            throw new IllegalStateException(
                    "Customer has no accounts."
            );
        }

        return accountIds.get(
                accountIds.size() - 1
        );
    }

}
