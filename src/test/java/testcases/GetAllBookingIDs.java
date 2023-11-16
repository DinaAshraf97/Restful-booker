package testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetAllBookingIDs extends TestBase {
    @Test(priority = 1, description = "get all booking IDs with valid method")
    public void getAllBookingIDsSuccessfully() {
        Response res =
                given()
                        .contentType("application/json").log().all()
                        .when()
                        .get("/booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .body("[0]", hasKey("bookingid")
                                ,"[0].bookingid",isA(Integer.class)
                                , "[0].bookingid", notNullValue())
                        .extract().response();
    }
    //Negative scenarios
    @Test(priority = 2, description = "get all booking IDs with invalid post method")
    public void getAllBookingIDsWithPostMethod() {
        Response errorMsg =
                given()
                        .contentType("application/json").log().all()
                .when()
                        .post("/booking")
                .then()
                        .log().all()
                        .assertThat().statusCode(404)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Not Found");
    }
    @Test(priority = 3, description = "get all booking IDs with invalid put method")
    public void getAllBookingIDsWithPutMethod() {
        Response errorMsg =
                given()
                        .contentType("application/json").log().all()
                        .when()
                        .put("/booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(404)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Not Found");
    }

}