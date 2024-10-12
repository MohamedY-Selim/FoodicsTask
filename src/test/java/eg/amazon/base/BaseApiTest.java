package eg.amazon.base;

import eg.amazon.api.AuthApi;
import eg.amazon.utils.ConfigUtils;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigUtils.getInstance().getAPIBaseUrl();

        token = new AuthApi().loginAndGetToken();

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Failed to fetch authentication token.");
        }
    }
}
