package eg.amazon.testcases;

import eg.amazon.config.EndPoint;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import eg.amazon.base.BaseApiTest;

import static io.restassured.RestAssured.given;
import static eg.amazon.config.EndPoint.WHOAMI_API_END_POINT;

public class WhoAmIApiTest extends BaseApiTest {

    @Test
    public void testWhoAmIWithToken() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .when()
                .get(WHOAMI_API_END_POINT);

        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200");
    }

    @Test
    public void testWhoAmIWithoutToken() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get(EndPoint.WHOAMI_API_END_POINT);

        if (response.statusCode() == 200) {
            Assert.fail("Authentication might not be enforced. Received 200 OK.");
        } else {
            Assert.assertEquals(response.statusCode(), 401, "Expected status code 401");
        }
    }
}
