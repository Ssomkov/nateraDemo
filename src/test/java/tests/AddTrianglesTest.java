package tests;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.emptyString;

public class AddTrianglesTest extends BaseTest {

    @BeforeClass
    public void beforeTest() {
        deleteAllTriangles();
    }

    @AfterClass
    public void afterTest() {
        deleteAllTriangles();
    }

    @Test(description = "check add triangle with integer sides")
    public void checkIntTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(5, 7, 10);
        responseShouldHave
                .statusCode(200)
                .body("firstSide", equalTo(5.0f))
                .body("secondSide", equalTo(7.0f))
                .body("thirdSide", equalTo(10.0f))
                .body("id", is(not(emptyString())));
    }

    @Test(description = "check add triangle with float sides")
    public void checkEqualFloatTriangle() {
        ValidatableResponse responseShouldHave = addTriangle("{\"input\":\"3.1; 3.1; 3.1\"}");
        responseShouldHave
                .statusCode(200)
                .body("firstSide", equalTo(4.2f))
                .body("secondSide", equalTo(4.2f))
                .body("thirdSide", equalTo(4.2f))
                .body("id", is(not(emptyString())));
    }

    @Test(description = "check add triangle with one negative length side")
    public void checkNegativeLengthSideTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(-3, 4, 5);
        responseShouldHave
                .statusCode(422)
                .body("id", is(not(emptyString())))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"));
    }

    @Test(description = "check add triangle with all zero sides")
    public void checkFullZeroTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(0, 0, 0);
        responseShouldHave
                .statusCode(422)
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"))
                .body("id", is(not(emptyString())));
    }

    @Test(description = "check add triangle with one zero side")
    public void checkZeroTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(0, 4, 5);
        responseShouldHave
                .statusCode(422)
                .body("id", is(not(emptyString())))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"));
    }

    @Test(description = "check add triangle that is a line")
    public void checkFormLineTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(2, 3, 5);
        responseShouldHave
                .statusCode(422)
                .body("id", is(not(emptyString())))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"));
    }

    @Test(description = "check add nonexistent triangle")
    public void checkNonexistentTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(5, 7, 27);
        responseShouldHave
                .statusCode(422)
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"))
                .body("id", is(not(emptyString())));
    }

    @Test(description = "check alternative separator")
    public void checkAnotherSeparator() {
        ValidatableResponse responseShouldHave = addTriangle(5, 7, 10, ",");
        responseShouldHave
                .statusCode(200)
                .body("firstSide", equalTo(5.0f))
                .body("secondSide", equalTo(7.0f))
                .body("thirdSide", equalTo(10.0f))
                .body("id", is(not(emptyString())));
    }

    @Test(description = "check no separator")
    public void checkNoSeparator() {
        ValidatableResponse responseShouldHave = addTriangle(5, 7, 10, "");
        responseShouldHave
                .statusCode(422)
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"))
                .body("id", is(not(emptyString())));
    }
}
