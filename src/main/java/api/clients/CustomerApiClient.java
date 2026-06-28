package api.clients;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CustomerApiClient {

    public Response getCustomerById(
            String customerId
    ) {
        return given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/customers/" + customerId)
                .then()
                .extract()
                .response();
    }

    public Response  getAccountsByCustomerId(
            String customerId
    ) {

        return given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/customers/" + customerId + "/accounts")
                .then()
                .extract()
                .response();
    }

    public String getLatestAccountIdByCustomerId(
            String customerId
    ) {

        Response response =
                getAccountsByCustomerId(customerId);

        List<String> accountIds =
                response.xmlPath()
                        .getList("accounts.account.id");

        return accountIds.get(
                accountIds.size() - 1
        );
    }

}
