package ApiTests;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class SpartanJsonToCollections {

    @BeforeMethod
    public void setUp(){

        baseURI="http://3.86.113.235:8000";
    }

    @Test
    public void test1(){

        Response response=given().accept(ContentType.JSON)
                .pathParam("id",11)
                .and().when().get("/api/spartans/{id}");

        //convert Json response to Java collections(Map)
        Map<String,Object> spartanMap= response.body().as(Map.class);

        Assert.assertEquals(spartanMap.get("id"),11.0);
        Assert.assertEquals(spartanMap.get("name"),"Nona");
    }
    @Test
    public void test2(){

       Response response= given().accept(ContentType.JSON)
                .when().get("/api/spartans");
        //convert full json body to list fo maps
        List<Map<String,Object>> listOfSprants=response.body().as(List.class);

        Map<String,Object> firstSprants=listOfSprants.get(0);
        System.out.println(firstSprants.get("name"));

        int counter=1;
        for (Map<String, Object> map : listOfSprants) {
            System.out.println(counter+" - spartan "+ map);
            counter++;
        }


    }
}
