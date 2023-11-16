package testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateToken extends TestBase {
@Test(priority = 1,description="create token with valid username and valid password")
    public void createTokenSuccessfully(){
    HashMap<String,String> loginData = new HashMap<>();
    loginData.put("username","admin");
    loginData.put("password","password123");
        Response res=
                given()
                        .contentType("application/json")
                        .body(loginData).log().all()
                .when()
                        .post("/auth")
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .body("",hasKey("token")
                                ,"token",isA(String.class)
                                ,"token",notNullValue())
                        .extract().response();
        String Token=res.path("token");
    }
    //Negative scenarios
    @Test(priority = 2,description="create token with invalid username")
    public void createTokenWithInvalidUserName(){
        HashMap<String,String> loginData = new HashMap<>();
        loginData.put("username","admin111");
        loginData.put("password","password123");
        given()
                .contentType("application/json")
                .body(loginData).log().all()
        .when()
                .post("/auth")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("",hasKey("reason")
                        ,"token",isA(String.class)
                        ,"reason",equalTo("Bad credentials"));
    }
    @Test(priority = 3,description="create token with invalid password")
    public void createTokenWithInvalidPassword(){
        HashMap<String,String> loginData = new HashMap<>();
        loginData.put("username","admin");
        loginData.put("password","password");
        given()
                .contentType("application/json")
                .body(loginData).log().all()
        .when()
                .post("/auth")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("",hasKey("reason")
                        ,"token",isA(String.class)
                        ,"reason",equalTo("Bad credentials"));
    }
    @Test(priority = 4,description="create token with invalid put method")
    public void createTokenWithPutMethod(){
        HashMap<String,String> loginData = new HashMap<>();
        loginData.put("username","admin");
        loginData.put("password","password123");
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(loginData).log().all()
                .when()
                        .put("/auth")
                .then()
                        .log().all()
                        .assertThat().statusCode(404).extract().response();
        Assert.assertEquals(errorMsg.asString(),"Not Found");
    }
    @Test(priority = 5,description="create token with invalid get method")
    public void createTokenWithGetMethod(){
        HashMap<String,String> loginData = new HashMap<>();
        loginData.put("username","admin");
        loginData.put("password","password123");
        Response errorMsg=
                given()
                        .contentType("application/json")
                        .body(loginData).log().all()
                        .when()
                        .get("/auth")
                        .then()
                        .log().all()
                        .assertThat().statusCode(404).extract().response();
        Assert.assertEquals(errorMsg.asString(),"Not Found");
    }
}
