package api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * API client for transfer-related operations.
 */
public class TransferApiClient {

    /**
     * Transfer funds between two accounts.
     *
     * @param fromAccountId Source account ID.
     * @param toAccountId   Destination account ID.
     * @param amount        Transfer amount.
     * @return Raw API response.
     */
    public Response transferFunds(
            String fromAccountId,
            String toAccountId,
            String amount
    ) {

            return RestAssured.given()
                    .log().all()
                    .header("Accept", "application/xml")
                    .queryParam("fromAccountId", fromAccountId)
                    .queryParam("toAccountId", toAccountId)
                    .queryParam("amount", amount)
                    .when()
                    .post("/transfer")
                    .then()
                    .extract()
                    .response();
    }
}
