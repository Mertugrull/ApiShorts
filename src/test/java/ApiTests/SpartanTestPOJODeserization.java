package ApiTests;

import static io.restassured.RestAssured.*;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class SpartanTestPOJODeserization {


    @BeforeClass
    public void setUpUrl(){
        baseURI="http://3.86.113.235:8000";
    }

            @Test
            public void test1(){

        Response response=given().accept(ContentType.JSON)
                .and().pathParam("id",15)
                .when().get("/api/spartans/{id}");

        //how to convert json response to our spartan class
                Spartan spartan1= response.body().as(Spartan.class);

                Assert.assertEquals(spartan1.getId(),15);

    }


    @Test
    public void gsonExample(){

        Gson gson=new Gson();

        String myJsonBody="{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";
        //using gson method do deserialization our json body
        Spartan spartanMeta=gson.fromJson(myJsonBody, Spartan.class);
        System.out.println(spartanMeta.getId());

        //serialization java object---> JSON BODY

        Spartan spartan= new Spartan(101,"Mike","Male",7365524128l);
        String jsonBody= gson.toJson(spartan);
        System.out.println(jsonBody);





    }

}
