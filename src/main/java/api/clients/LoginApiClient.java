package api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * API client for login-related operations.
 */
public class LoginApiClient {

    /**
     * Login with username and password.
     *
     * @return Raw API response.
     */
    public Response login(String username, String password) {

        return RestAssured.given()
                .header("Accept", "application/xml")
                .when()
                .get("/login/{username}/{password}",
                        username,
                        password)
                .then()
                .extract()
                .response();

    }

    /**
     * Login and return the customer ID from the response.
     */
    public String getCustomerId(String username, String password) {

        Response response = login(username, password);

        return response.xmlPath().getString("customer.id");
    }
}