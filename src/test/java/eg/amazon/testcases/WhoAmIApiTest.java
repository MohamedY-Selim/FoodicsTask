package eg.amazon.testcases;

import eg.amazon.base.BaseApiTest;
import eg.amazon.api.WhoAmIApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WhoAmIApiTest extends BaseApiTest {

    private WhoAmIApi whoAmIApi = new WhoAmIApi();

    @Test(description = "Verify 'whoami' endpoint with valid token")
    public void testWhoAmIWithToken() {
        Response response = whoAmIApi.getUserInfo(token);

        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200.");
        Assert.assertEquals(response.jsonPath().getString("email"), "merchant@foodics.com",
                "Email should match the logged-in user.");
    }

    @Test(description = "Verify 'whoami' endpoint without token returns 401")
    public void testWhoAmIWithoutToken() {
        Response response = whoAmIApi.getUserInfoWithoutToken();

        Assert.assertEquals(response.statusCode(), 401, "Expected status code 401.");
        Assert.assertTrue(response.jsonPath().getString("message").contains("Unauthorized"),
                "Message should contain 'Unauthorized'.");
    }
}
