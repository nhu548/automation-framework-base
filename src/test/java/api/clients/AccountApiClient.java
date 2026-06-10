package api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountApiClient {

    public Response  getAccountById(String accountId) {

        return given()
                .header("Accept", "application/xml")
                .when()
                .get("/accounts/" + accountId)
                .then()
                .extract()
                .response();
    }
}