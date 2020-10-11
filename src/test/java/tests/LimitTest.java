package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.emptyString;

public class LimitTest extends BaseTest {

    @BeforeClass
    public void beforeTest() {
        deleteAllTriangles();
    }

    @AfterClass
    public void afterTest() {
        deleteAllTriangles();
    }

    @Test(description = "check adding more triangles than allowed")
    public void checkAddTrianglesOverLimit() {
        for (int i = 1; i <= 10; i++) addTriangle(5, 7, 10).statusCode(200);
        addTriangle(5, 7, 10)
                .statusCode(422)
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Limit exceeded"))
                .body("id", is(not(emptyString())));
    }
}
