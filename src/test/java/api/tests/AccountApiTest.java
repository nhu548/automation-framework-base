package api.tests;

import api.base.BaseApiTest;
import api.clients.AccountApiClient;
import api.testdata.ApiTestData;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountApiTest extends BaseApiTest {

    private final AccountApiClient accountApiClient =
            new AccountApiClient();

    @Test(
            description =
                    "API01 - Verify account details can be retrieved successfully"
    )
    public void verifyGetAccountDetailsSuccessfully() {

        Response response =
                accountApiClient.getAccountById(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200"
        );

        XmlPath xmlPath =
                new XmlPath(response.asString());

        Assert.assertEquals(
                xmlPath.getString("account.id"),
                ApiTestData.VALID_ACCOUNT_ID,
                "Account ID should match"
        );

        Assert.assertNotNull(
                xmlPath.getString("account.customerId"),
                "Customer ID should not be null"
        );

        Assert.assertNotNull(
                xmlPath.getString("account.type"),
                "Account type should not be null"
        );

        Assert.assertNotNull(
                xmlPath.getString("account.balance"),
                "Balance should not be null"
        );
    }
}