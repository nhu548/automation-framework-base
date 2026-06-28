package api.clients;

import io.restassured.response.Response;
import org.testng.Assert;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class AccountApiClient {

    public Response getAccountById(
            String accountId
    ) {

        return given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/accounts/" + accountId)
                .then()
                .extract()
                .response();
    }

    public Response createNewAccount(
            String customerId,
            String accountType,
            String fromAccountId
    ) {

        return given()
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

    public String getCustomerIdByAccountId(
            String accountId
    ) {

        Response response =
                getAccountById(accountId);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200 when retrieving source account"
        );

        String customerId =
                response.xmlPath().getString("account.customerId");

        assertFieldIsNotBlank(
                customerId,
                "Customer ID"
        );

        return customerId;
    }

    public void assertFieldIsNotBlank(
            String actualValue,
            String fieldName
    ) {

        Assert.assertNotNull(
                actualValue,
                fieldName + " should not be null"
        );

        Assert.assertFalse(
                actualValue.trim().isEmpty(),
                fieldName + " should not be empty"
        );
    }

    public String createNewCheckingAccount(
            String sourceAccountId
    ) {

        String customerId =
                getCustomerIdByAccountId(
                        sourceAccountId
                );

        Response response =
                createNewAccount(
                        customerId,
                        ApiTestData.CHECKING_ACCOUNT_TYPE,
                        sourceAccountId
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Create account status code should be 200"
        );

        String newAccountId =
                response.xmlPath().getString("account.id");

        assertFieldIsNotBlank(
                newAccountId,
                "New account ID"
        );

        return newAccountId;
    }

    public BigDecimal getAccountBalance(
            String accountId
    ) {

        Response response =
                getAccountById(accountId);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200 when retrieving account balance"
        );

        String balance =
                response.xmlPath().getString("account.balance");

        assertFieldIsNotBlank(
                balance,
                "Account balance"
        );

        return new BigDecimal(balance);
    }
}