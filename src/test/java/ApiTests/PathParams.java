package ApiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PathParams {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI="http://3.86.113.235:8000";
    }

    @Test
    public void pathTest1(){
        Response response= RestAssured.given().accept(ContentType.JSON)
                        .and().pathParam("id",18)
                        .when().get("/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        Assert.assertTrue(response.body().prettyPrint().contains("Allen"));
    }

    @Test
    public void negativePathParamTest(){

        Response response=RestAssured.given().contentType(ContentType.JSON)
                .and().pathParam("id",500)
                .when().get("/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json");
        System.out.println("response.body().prettyPrint() = " + response.body().prettyPrint());



    }

    @Test
    public void test2(){
        Response response= RestAssured.given().accept(ContentType.JSON)
                        .pathParam("id",15)
                                .when().get("api/spartans/{id}");

        System.out.println(response.body().prettyPrint());
    }



}
