package tests.common;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import helpers.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import models.Triangle;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private static String user;
    private static String token;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = Config.getApiUrl();
        user = Config.getApiUser();
        token = Config.getApiUserToken();
    }

    private static RequestSpecification getBasicRequest() {
        return given().header(user, token);
    }

    @Step("Get triangle with id: [{0}]")
    public static ValidatableResponse getTriangle(String id) {
        return getBasicRequest().get("/" + id).then();
    }

    @Step("Get all triangles")
    public static ValidatableResponse getAllTriangles() {
        return getBasicRequest().get("/all").then();
    }

    @Step("Get triangle area with id: [{0}]")
    public static ValidatableResponse getTriangleArea(String id) {
        return getBasicRequest().get("/" + id + "/area").then();
    }

    @Step("Get triangle perimeter with id: [{0}]")
    public static ValidatableResponse getTrianglePerimeter(String id) {
        return getBasicRequest().get("/" + id + "/perimeter").then();
    }

    @Step("Delete triangle perimeter with id: [{0}]")
    public static ValidatableResponse deleteTriangle(String id) {
        return getBasicRequest().delete("/" + id).then();
    }

    @Step("Get triangle id with sides: [{3}], [{4}], [{5}]")
    public String getTestTriangleId() {
        return getTestTriangleId(3, 4, 5);
    }

    @Step("Get triangle with sides: [{0}], [{1}], [{2}]")
    public String getTestTriangleId(int a, int b, int c) {
        return getTestTriangleId(addTriangle(a, b, c));
    }

    @Step("Get triangle id from response")
    public String getTestTriangleId(ValidatableResponse creationResponse) {
        return new JSONObject(creationResponse.extract().asString()).getString("id");
    }

    @Step("Add triangle with sides: [{0}], [{1}], [{2}]")
    public ValidatableResponse addTriangle(int a, int b, int c) {
        return addTriangle(a, b, c, null);
    }

    @Step("Add triangle by text data: [{0}]")
    public ValidatableResponse addTriangle(String body) {
        return getBasicRequest()
                .header("Content-Type", "application/json;")
                .body(body)
                .post()
                .then();
    }

    @Step("Add triangle with sides: [{0}], [{1}], [{2}] and separator: [{3}]")
    public ValidatableResponse addTriangle(int a, int b, int c, String separator) {
        JSONObject jsonObject = new JSONObject();
        String tempSeparator = ";";
        if (separator != null) {
            tempSeparator = separator;
            jsonObject.put("separator", tempSeparator);
        }
        String input = a + tempSeparator + b + tempSeparator + c;
        jsonObject.put("input", input);
        return addTriangle(jsonObject.toString());
    }

    @Step("Delete all triangles")
    public static void deleteAllTriangles() {
        ValidatableResponse response = getAllTriangles();
        response.statusCode(200);
        Type triangleListType = new TypeToken<List<Triangle>>() {
        }.getType();
        ArrayList<Triangle> allTriangles = new Gson().fromJson(response.extract().asString(), triangleListType);
        ArrayList<Thread> allTasks = new ArrayList<>();
        for (Triangle triangle : allTriangles) {
            Thread thread = new Thread(
                    () -> deleteTriangle(triangle.getId())
            );
            thread.start();
            allTasks.add(thread);
        }
        for (Thread thread : allTasks) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {

            }
        }
    }

}
