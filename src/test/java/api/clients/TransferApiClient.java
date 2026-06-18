package api.clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TransferApiClient {

    public Response transferFunds(
            String fromAccountId,
            String toAccountId,
            String amount
    ){

            return given()
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
