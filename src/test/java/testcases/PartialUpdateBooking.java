package testcases;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PartialUpdateBooking extends TestBase {
@Test(priority = 1,description="Partial Update booking with authorized account, valid method and valid data fields")
    public void PartialUpdateBookingSuccessfully() throws IOException {
    File PartialUpdatebookingData=new File("src/test/java/resources/PartialUpdateBookingData.json");
    String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/PartialUpdateBookingData.json")));
    // Convert the expected JSON string to a JSONObject
    JSONObject expectedJsonObj = new JSONObject(expectedJSON);

    given()
                .contentType("application/json")
                .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body(PartialUpdatebookingData).log().all()
        .when()
                .patch("/Booking/3")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body(
                        "",hasKey("firstname")
                        ,"",hasKey("lastname")
                        ,"",hasKey("totalprice")
                        ,"",hasKey("depositpaid")
                        ,"bookingdates",hasKey("checkin")
                        ,"bookingdates.",hasKey("checkout")
                        ,"",hasKey("additionalneeds")
                        ,"firstname",notNullValue()
                        ,"lastname",notNullValue()
                        ,"totalprice",notNullValue()
                        ,"depositpaid",notNullValue()
                        ,"bookingdates.checkin",notNullValue()
                        ,"bookingdates.checkout",notNullValue()
                        ,"firstname",isA(String.class)
                        ,"lastname",isA(String.class)
                        ,"totalprice",isA(Integer.class)
                        ,"depositpaid",isA(Boolean.class)
                        ,"bookingdates.checkin",isA(String.class)
                        ,"bookingdates.checkout",isA(String.class)
                        ,"additionalneeds",isA(String.class)
                        ,"firstname",equalTo(expectedJsonObj.get("firstname"))
                        ,"lastname",equalTo(expectedJsonObj.get("lastname"))
                        ,"additionalneeds",equalTo(expectedJsonObj.get("additionalneeds"))
                );
    }
    //Negative Scenarios
    @Test(priority = 2,description="Partial Update booking with unauthorized account")
    public void PartialUpdateBookingWithUnautharizedAccount() {
        File PartialUpdatebookingData=new File("src/test/java/resources/PartialUpdateBookingData.json");

            Response errorMsg =
                    given()
                            .contentType("application/json")
                            .body(PartialUpdatebookingData).log().all()
                    .when()
                            .patch("/Booking/3")
                    .then()
                            .log().all()
                            .assertThat().statusCode(403)
                            .extract().response();
            Assert.assertEquals(errorMsg.asString(), "Forbidden");
        }
    @Test(priority = 3,description="Partial Update booking with invalid post method")
    public void PartialUpdateBookingWithPostMethod() {
        File PartialUpdatebookingData=new File("src/test/java/resources/PartialUpdateBookingData.json");

        Response errorMsg =
                given()
                        .contentType("application/json")
                        .body(PartialUpdatebookingData).log().all()
                        .when()
                        .post("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(404)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(), "Not Found");
    }

}
