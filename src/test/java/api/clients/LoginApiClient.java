package api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginApiClient {

    public Response login(
            String username,
            String password
    ) {

        return given()
                .header("Accept", "application/xml")
                .when()
                .get("/login/" + username + "/" + password)
                .then()
                .extract()
                .response();

    }

    public String getCustomerId(
            String username,
            String password
    ) {
        Response response =
                login(
                        username,
                        password
                );

        return response.xmlPath().getString("customer.id");
    }
}