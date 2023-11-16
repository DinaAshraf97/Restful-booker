package testcases;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteBooking extends TestBase {
    @Test(priority = 1, description = "Delete booking with authorized account and valid method")
    public void DeleteBookingSuccessfully() {
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .delete("/Booking/100")
                        .then()
                        .log().all()
                        .assertThat().statusCode(201)
                        .extract().response();
        Assert.assertEquals(res.asString(), "Created");
    }

    //Negative Scenarios
    @Test(priority = 2, description = "Delete booking with unauthorized account")
    public void DeleteBookingWithUnauthorizedAccount() {
        Response errorMsg =
                given()
                        .contentType("application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .delete("/Booking/100")
                        .then()
                        .log().all()
                        .assertThat().statusCode(405)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(), "Method Not Allowed");
    }

    @Test(priority = 3, description = "Delete booking with invalid post method")
    public void DeleteBookingWithPostMethod() {
        Response errorMsg =
                given()
                        .contentType("application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .post("/Booking/100")
                        .then()
                        .log().all()
                        .assertThat().statusCode(404)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(), "Not Found");
    }

    @Test(priority = 3, description = "Delete booking with invalid get method")
    public void DeleteBookingWithGettMethod() {
        Response errorMsg =
                given()
                        .contentType("application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .get("/Booking/100")
                        .then()
                        .log().all()
                        .assertThat().statusCode(404)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(), "Not Found");
    }

    @Test(priority = 3, description = "Delete booking with invalid put method")
    public void DeleteBookingWithPutMethod() {
        Response errorMsg =
                given()
                        .contentType("application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .post("/Booking/100")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(), "Bad Request");
    }
}
