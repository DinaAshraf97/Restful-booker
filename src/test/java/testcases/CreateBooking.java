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

public class CreateBooking extends TestBase {
@Test(priority = 1,description="create booking with valid method and valid data fields")
    public void createBookingSuccessfully() throws IOException {
    File bookingData=new File("src/test/java/resources/BookingData.json");
    String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/BookingData.json")));
    // Convert the expected JSON string to a JSONObject
    JSONObject expectedJsonObj = new JSONObject(expectedJSON);

    given()
                .contentType("application/json")
                .body(bookingData).log().all()
        .when()
                .post("/Booking")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body(
                        "booking",hasKey("firstname")
                        ,"booking",hasKey("lastname")
                        ,"booking",hasKey("totalprice")
                        ,"booking",hasKey("depositpaid")
                        ,"booking.bookingdates",hasKey("checkin")
                        ,"booking.bookingdates.",hasKey("checkout")
                        ,"booking",hasKey("additionalneeds")
                        ,"bookingid",notNullValue()
                        ,"booking.firstname",notNullValue()
                        ,"booking.lastname",notNullValue()
                        ,"booking.totalprice",notNullValue()
                        ,"booking.depositpaid",notNullValue()
                        ,"booking.bookingdates.checkin",notNullValue()
                        ,"booking.bookingdates.checkout",notNullValue()
                        ,"bookingid",isA(Integer.class)
                        ,"booking.firstname",isA(String.class)
                        ,"booking.lastname",isA(String.class)
                        ,"booking.totalprice",isA(Integer.class)
                        ,"booking.depositpaid",isA(Boolean.class)
                        ,"booking.bookingdates.checkin",isA(String.class)
                        ,"booking.bookingdates.checkout",isA(String.class)
                        ,"booking.additionalneeds",isA(String.class)
                        ,"booking.firstname",equalTo(expectedJsonObj.get("firstname"))
                        ,"booking.lastname",equalTo(expectedJsonObj.get("lastname"))
                        ,"booking",hasEntry("totalprice",expectedJsonObj.getInt("totalprice"))
                        ,"booking",hasEntry("depositpaid",expectedJsonObj.getBoolean("depositpaid"))
                        ,"booking.bookingdates",hasEntry("checkin",(expectedJsonObj.getJSONObject("bookingdates").get("checkin")))
                        ,"booking.bookingdates",hasEntry("checkout",(expectedJsonObj.getJSONObject("bookingdates").get("checkout")))
                        ,"booking.additionalneeds",equalTo(expectedJsonObj.get("additionalneeds"))
                );
    }
    //Negative Scenarios
        @Test(priority = 2,description="create booking with get invalid method")
        public void createBookingWithGetMethod() {
            File bookingData = new File("src/test/java/resources/BookingData.json");
            given()
                    .contentType("application/json")
                    .body(bookingData).log().all()
            .when()
                    .get("/Booking")
            .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .body(
                            "[0]", hasKey("bookingid")
                    );
        }
    @Test(priority = 3,description="create booking with put invalid method")
    public void createBookingWithPutMethod() {
        File bookingData = new File("src/test/java/resources/BookingData.json");
        Response errorMsg= given()
                .contentType("application/json")
                .body(bookingData).log().all()
        .when()
                .put("/Booking")
        .then()
                .log().all()
                .assertThat().statusCode(404).extract().response();
        Assert.assertEquals(errorMsg.asString(),"Not Found");
    }
    @Test(priority = 4,description="create booking with patch invalid method")
    public void createBookingWithPatchMethod() {
        File bookingData = new File("src/test/java/resources/BookingData.json");
        Response errorMsg= given()
                .contentType("application/json")
                .body(bookingData).log().all()
                .when()
                .put("/Booking")
                .then()
                .log().all()
                .assertThat().statusCode(404).extract().response();
        Assert.assertEquals(errorMsg.asString(),"Not Found");
    }
        @Test(description="create booking with invalid First Name")
        public void createBookingWithInvalidFirstName() throws IOException {
            String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
            // Convert the expected JSON string to a JSONObject
            JSONArray expectedJsonArray = new JSONArray(expectedJSON);
            JSONObject jsonObj= expectedJsonArray.getJSONObject(0);
            Response errorMsg=
            given()
                    .contentType("application/json")
                    .body(jsonObj.toString()).log().all()
            .when()
                    .post("/Booking")
            .then()
                    .log().all()
                    .assertThat().statusCode(500)
                    .extract().response();
            Assert.assertEquals(errorMsg.asString(),"Internal Server Error");
        }
    @Test(description="create booking with invalid Last Name")
    public void createBookingWithInvalidLastName() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(2);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(500)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Internal Server Error");
    }
    @Test(description="create booking with invalid Total Price")
    public void createBookingWithInvalidTotalPrice() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(4);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with invalid Deposit paid")
    public void createBookingWithInvalidDepositPaid() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(6);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking invalid month in check-in date")
    public void createBookingWithInvalidMonthInCheckin() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(9);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with invalid old check-in date")
    public void createBookingWithOldCheckinDate() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(11);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with invalid Additional")
    public void createBookingWithInvalidAdditional() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(12);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with invalid First Name")
    public void createBookingWithEmptyFirstName() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(13);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with invalid Last Name")
    public void createBookingWithEmptyLastName() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(14);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with Empty Total Price")
    public void createBookingWithEmptyTotalPrice() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(15);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with Empty Deposit paid")
    public void createBookingWithEmptyDepositPaid() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(16);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with Empty check-in date")
    public void createBookingWithEmptyCheckin() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(17);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
    @Test(description="create booking with Empty check-out date")
    public void createBookingWithEmptyCheckoutDate() throws IOException {
        String expectedJSON = new String(Files.readAllBytes(Paths.get("src/test/java/resources/InvalidBookingData.json")));
        // Convert the expected JSON string to a JSONObject
        JSONArray expectedJsonArray = new JSONArray(expectedJSON);
        JSONObject jsonObj= expectedJsonArray.getJSONObject(18);
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(jsonObj.toString()).log().all()
                        .when()
                        .post("/Booking")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();
        Assert.assertEquals(errorMsg.asString(),"Bad Request");
    }
}
