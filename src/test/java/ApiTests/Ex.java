package ApiTests;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class Ex {

    @BeforeClass
    public void setUpUrl(){
        baseURI="http://3.86.113.235:8000";
    }

    @Test
    public void test1() {

        Response response=given().accept(ContentType.JSON)
                .and().pathParam("id",10)
                .when().get("api/spartans/{id}");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        int id=response.path("id");
        String name=response.path("name");
        String gender=response.path("gender");
        long phone=response.path("phone");

        Assert.assertEquals(id,10);

    }

    @Test
    public void test2(){

        Response response=get("api/spartans");

        int id=response.path("id[9]");
        System.out.println(id);

        String firstName=response.path("name[0]");
        System.out.println(firstName);

        List<Map<String, Object>> allNames=response.path("name");
        System.out.println(allNames);
    }

    @Test
            public void test3() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("api/spartans/{id}");

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("name"),"Lorenza");
    }


    @Test
    public void test4(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",15)
                .when().get("api/spartans/{id}")
                .then().assertThat().statusCode(200)
                .assertThat().contentType("application/json")
                .assertThat().body("id", Matchers.equalTo(15))
                .assertThat().body("name",Matchers.equalTo("Meta"))
                .assertThat().body("gender",Matchers.equalTo("Female"));

    }



}
