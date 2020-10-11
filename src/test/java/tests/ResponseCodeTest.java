package tests;

import helpers.Config;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import static io.restassured.RestAssured.given;

public class ResponseCodeTest extends BaseTest {

    @AfterClass
    public void afterTest() {
        deleteAllTriangles();
    }

    @Test(description = "check status code 404")
    public void check404StatusCode() {
        ValidatableResponse responseShouldHave = given()
                .header(Config.getApiUser(), Config.getApiUserToken())
                .body("{5;7;10}")
                .get("312123")
                .then();
        responseShouldHave.statusCode(404);
    }

    @Test(description = "check status code 200")
    public void check200StatusCode() {
        addTriangle(5, 7, 10).statusCode(200);
    }

    @Test(description = "check status code 401")
    public void check401StatusCode() {
        given()
                .body("{5;7;10}")
                .post()
                .then()
                .statusCode(401);
    }

    @Test(description = "check status code 422")
    public void check422StatusCode() {
        addTriangle(2, 3, 5).statusCode(422);
    }
}
