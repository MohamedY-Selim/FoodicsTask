package eg.amazon.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class WhoAmIApi {

    private static final String BASE_URL = "https://pay2.foodics.dev/cp_internal";

    public Response getUserInfoWithToken() {
        String token = "Lyz22cfYKMetFhKQybx5HAmVimF1i0xO";

        return given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/whoami")
                .then()
                .extract().response();
    }

    public Response getUserInfoWithoutToken() {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/whoami")
                .then()
                .extract().response();
    }
}
