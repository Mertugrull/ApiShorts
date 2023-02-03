package ApiTests;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HamCrest {

    @BeforeMethod
    public void setUpUrl(){
        baseURI="http://3.86.113.235:8000";
    }

    @Test
    public void test1(){

        given().accept(ContentType.JSON).
                pathParam("id",15)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .assertThat().contentType("application/json");
    }

    @Test
    public void test2(){
        given().accept(ContentType.JSON)
                .pathParam("id",15)
                .when().get("/api/spartans/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().body("id", Matchers.equalTo(15),"name",Matchers.equalTo("Meta")
                ,"gender",Matchers.equalTo("Female"),"phone",Matchers.equalTo(1938695106));

    }



}
