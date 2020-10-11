package tests;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import static org.hamcrest.Matchers.equalTo;

public class PropertiesTest extends BaseTest {

    @BeforeClass
    public void beforeTest() {
        deleteAllTriangles();
    }

    @AfterClass
    public void afterTest() {
        deleteAllTriangles();
    }

    @Test(description = "check triangle area")
    public void checkTriangleArea() {
        getTriangleArea(getTestTriangleId(3, 4, 5))
                .statusCode(200)
                .body("result", equalTo(6.0f));
    }

    @Test(description = "check triangle repimeter")
    public void checkTrianglePerimeter() {
        getTrianglePerimeter(getTestTriangleId(3, 4, 5))
                .statusCode(200)
                .body("result", equalTo(12.0f));
    }

    @Test(description = "checking for getting a triangle by ID")
    public void checkTriangleID() {
        String id = getTestTriangleId(5, 7, 10);
        getTriangle(id)
                .statusCode(200)
                .body("id", equalTo(id))
                .body("firstSide", equalTo(5.0f))
                .body("secondSide", equalTo(7.0f))
                .body("thirdSide", equalTo(10.0f));
    }

    @Test(description = "check list triangles")
    public void checkListTriangles() {
        String testId = getTestTriangleId();
        ValidatableResponse response = getAllTriangles();
        response.statusCode(200);
        Assert.assertTrue(response.extract().asString().contains(testId));
    }

    @Test(description = "check removal of an existing triangle")
    public void checkDeleteExistingTriangle() {
        String testId = getTestTriangleId();
        deleteTriangle(testId).statusCode(200);
    }

    @Test(description = "check removal of an non-existing triangle")
    public void deleteTriangleTest() {
        String testId = getTestTriangleId();
        deleteTriangle(testId).statusCode(200);
        getTriangle(testId).statusCode(404);
    }
}
