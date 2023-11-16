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

public class UpdateBooking extends TestBase {
@Test(priority = 1,description="Update booking with authorized account, valid method and valid data fields")
    public void UpdateBookingSuccessfully() throws IOException {
    File UpdatebookingData=new File("src/test/java/resources/UpdateBookingData.json");
    String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/UpdateBookingData.json")));
    // Convert the expected JSON string to a JSONObject
    JSONObject expectedJsonObj = new JSONObject(expectedJSON);

    given()
                .contentType("application/json")
                .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body(UpdatebookingData).log().all()
        .when()
                .put("/Booking/3")
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
                        ,"",hasEntry("totalprice",expectedJsonObj.getInt("totalprice"))
                        ,"",hasEntry("depositpaid",expectedJsonObj.getBoolean("depositpaid"))
                        ,"bookingdates",hasEntry("checkin",(expectedJsonObj.getJSONObject("bookingdates").get("checkin")))
                        ,"bookingdates",hasEntry("checkout",(expectedJsonObj.getJSONObject("bookingdates").get("checkout")))
                        ,"additionalneeds",equalTo(expectedJsonObj.get("additionalneeds"))
                );
    }
    //Negative Scenarios
        @Test(priority = 2,description="Update booking with unauthorized account")
        public void UpdateBookingWithUnauthorizedAccount() throws IOException {
            File UpdatebookingData = new File("src/test/java/resources/UpdateBookingData.json");

            Response errorMsg =
                    given()
                            .contentType("application/json")
                            .body(UpdatebookingData).log().all()
                            .when()
                            .put("/Booking/3")
                            .then()
                            .log().all()
                            .assertThat().statusCode(403)
                            .extract().response();
            Assert.assertEquals(errorMsg.asString(), "Forbidden");
        }
        @Test(priority = 3,description="Update booking with post invalid method")
        public void UpdateBookingWithPostMethod() {
            File bookingData = new File("src/test/java/resources/BookingData.json");
            Response errorMsg=
            given()
                    .contentType("application/json")
                    .body(bookingData).log().all()
            .when()
                    .post("/Booking/3")
            .then()
                    .log().all()
                    .assertThat().statusCode(404)
                    .extract().response();
            Assert.assertEquals(errorMsg.asString(), "Not Found");
        }

        @Test(description="Update booking with invalid First Name")
        public void UpdateBookingWithInvalidFirstName() throws IOException {
            String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
            // Convert the expected JSON string to a JSONObject
            JSONArray expectedJsonArray = new JSONArray(expectedJSON);
            JSONObject jsonObj= expectedJsonArray.getJSONObject(0);
            Response errorMsg=
            given()
                    .contentType("application/json")
                    .body(jsonObj.toString()).log().all()
                    .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
            .when()
                    .put("/Booking/3")
            .then()
                    .log().all()
                    .assertThat().statusCode(500)
                    .extract().response();
            Assert.assertEquals(errorMsg.asString(),"Internal Server Error");
        }
    @Test(description="Update booking with invalid Last Name")
    public void UpdateBookingWithInvalidLastName() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(2);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                        .put("/Booking/3")
                .then()
                        .log().all()
                        .assertThat().statusCode(500)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Internal Server Error");
    }
    @Test(description="Update booking with invalid Total Price")
    public void UpdateBookingWithInvalidTotalPrice() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(4);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                        .put("/Booking/3")
                .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with invalid Deposit paid")
    public void UpdateBookingWithInvalidDepositPaid() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(6);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                        .put("/Booking/3")
                .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking invalid month in check-in date")
    public void UpdateBookingWithInvalidMonthInCheckin() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(9);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                        .put("/Booking/3")
                .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with invalid old check-in date")
    public void UpdateBookingWithOldCheckinDate() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(11);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with invalid Additional")
    public void UpdateBookingWithInvalidAdditional() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(12);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with invalid First Name")
    public void UpdateBookingWithEmptyFirstName() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(13);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with invalid Last Name")
    public void UpdateBookingWithEmptyLastName() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(14);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with Empty Total Price")
    public void UpdateBookingWithEmptyTotalPrice() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(15);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with Empty Deposit paid")
    public void UpdateBookingWithEmptyDepositPaid() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(16);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with Empty check-in date")
    public void UpdateBookingWithEmptyCheckin() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(17);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="Update booking with Empty check-out date")
    public void UpdateBookingWithEmptyCheckoutDate() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(18);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .when()
                        .put("/Booking/3")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
}
