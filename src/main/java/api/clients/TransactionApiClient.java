package api.clients;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

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

    public List<String> getTransactionIdsByAccountId(
            String accountId
    ) {

        Response response =
                getTransactionsByAccountId(
                        accountId
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200 when retrieving transactions"
        );

        return response.xmlPath().getList(
                "transactions.transaction.id"
        );
    }

    // Get latest transaction ID by account ID
    public String getLatestTransactionIdByAccountId(
            String accountId
    ) {

        Response response =
                getTransactionsByAccountId(
                        accountId
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200 when retrieving transactions"
        );

        List<String> transactionIds =
                response.xmlPath().getList(
                        "transactions.transaction.id"
                );

        Assert.assertFalse(
                transactionIds.isEmpty(),
                "Transaction list should not be empty"
        );

        return transactionIds.get(
                transactionIds.size() - 1
        );
    }

    public String findNewTransactionId(
            List<String> transactionIdsBefore,
            List<String> transactionIdsAfter
    ) {

        for (String transactionId : transactionIdsAfter) {

            if (!transactionIdsBefore.contains(
                    transactionId
            )) {

                return transactionId;
            }
        }

        return null;
    }

    // Get transaction details by transaction ID
    public Response getTransactionById(
            String transactionId
    ) {

        return given()
                .log().all()
                .header("Accept", "application/xml")
                .when()
                .get("/transactions/" + transactionId)
                .then()
                .extract()
                .response();
    }


}
