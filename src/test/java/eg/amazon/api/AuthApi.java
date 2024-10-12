package eg.amazon.api;

import eg.amazon.config.EndPoint;
import eg.amazon.utils.ConfigUtils;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthApi {

    public String loginAndGetToken() {
        Response response =
                given()
                        .baseUri(ConfigUtils.getInstance().getAPIBaseUrl())
                        .header("Content-Type", "application/json")
                        .body("{\"email\": \"" + ConfigUtils.getInstance().getAPIEmail() + "\", " +
                                "\"password\": \"" + ConfigUtils.getInstance().getAPIPassword() + "\"}")
                        .when()
                        .post(EndPoint.LOGIN_API_END_POINT)
                        .then()
                        .extract().response();

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to login! Status code: " + response.statusCode());
        }

        return response.jsonPath().getString("token");
    }
}
