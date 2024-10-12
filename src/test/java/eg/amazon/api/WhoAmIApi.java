package eg.amazon.api;

import eg.amazon.config.EndPoint;
import eg.amazon.utils.ConfigUtils;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class WhoAmIApi {

    public Response getUserInfo(String token) {
        return given()
                .baseUri(ConfigUtils.getInstance().getAPIBaseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .get(EndPoint.WHOAMI_API_END_POINT)
                .then()
                .extract().response();
    }

    public Response getUserInfoWithoutToken() {
        return given()
                .baseUri(ConfigUtils.getInstance().getAPIBaseUrl())
                .when()
                .get(EndPoint.WHOAMI_API_END_POINT)
                .then()
                .extract().response();
    }
}
