package api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TransactionApiClient {

    //get transaction details by account id
    public Response getTransactionsByAccountId(
            String accountId
    ) {
        return given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/accounts/" + accountId + "/transactions")
                .then()
                .extract()
                .response();
    }

}
