package testcases;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    @BeforeMethod
    public static void baseURI(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
    }
}
