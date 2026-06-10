package api.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected static final String BASE_URL =
            "https://parabank.parasoft.com/parabank/services/bank";

    @BeforeClass
    public void setupApi() {

        RestAssured.baseURI = BASE_URL;
    }
}