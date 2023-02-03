package ApiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpartanTest {

    String spartanBaseUrl= "http://3.86.113.235:8000";

    @Test
    public void viewSpartan(){

        Response response = RestAssured.get(spartanBaseUrl + "/api/spartans");

        Assert.assertEquals(response.statusCode(),200);

        System.out.println("response.body().prettyPrint() = " + response.body().prettyPrint());

    }

    /*
    when user send GET rquest to /api/spartans endpoint
    Then status code must be 200
    And body should contains Allen
     */

    @Test
    public void viewSpartanTest2(){

        Response response = RestAssured.get(spartanBaseUrl + "/api/spartans");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.body().prettyPrint().contains("Allen"));

    }

    @Test
    public void viewSpartanTest3(){

       Response response=RestAssured.given().accept(ContentType.JSON).when().get(spartanBaseUrl+"/api/spartans");

       Assert.assertEquals(response.statusCode(),200);

       Assert.assertEquals(response.contentType(),"application/json");

    }

    @Test
    public void pathTest1(){

        Response response=RestAssured.given().accept(ContentType.JSON).and()

                .when().get("/api/spartans/{id}");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
        Assert.assertTrue(response.body().prettyPrint().equals("Allen"));



    }

    @Test
    public void repeatGetTest(){

        Response response=RestAssured.get(spartanBaseUrl+"/api/spartans");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.body().prettyPrint().contains("Allen"));

    }

    @Test
    public void contentType(){

        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanBaseUrl+"/api/spartans");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");


    }

    @Test
    public void pathParameter(){

        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().pathParam("id",18)
                .get(spartanBaseUrl+"/api/spartans/{id}");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().prettyPrint().contains("Allen"));

    }

    @Test
    public void pathParamTest(){

        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().pathParam("id",500)
                .when().get(spartanBaseUrl+"/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Not Found"));
    }

    @Test
    public void queryPath(){

        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().queryParam("gender","female")
                .and().queryParam("nameContains","J")
                .when().get(spartanBaseUrl+"/api/spartans/search");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Female"));
        Assert.assertTrue(response.body().asString().contains("Janette"));

    }
    @Test
    public void mapQuery(){

        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("gender","Female");
        paramsMap.put("nameContains","J");


        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().queryParams(paramsMap)
                .and().get(spartanBaseUrl+"/api/spartans/search");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Female"));
        Assert.assertTrue(response.body().asString().contains("Janette"));


    }

    @Test
    public void queryWithMap(){
        Map<String,Object> params=new HashMap<>();
        params.put("nameContains","M");
        params.put("gender","Male");

        Response response=RestAssured.given().accept(ContentType.JSON)
                .when().queryParams(params)
                .and().get(spartanBaseUrl+"/api/spartans/search");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Male"));

    }

    @Test
    public void pathMethod(){

       Response response= RestAssured.given().accept(ContentType.JSON)
                       .when().pathParam("id",10)
                       .and().get(spartanBaseUrl+"/api/spartans/{id}");

        System.out.println(response.body().path("id").toString());
        System.out.println(response.body().path("name").toString());
        System.out.println(response.body().path("gender").toString());
        System.out.println(response.body().path("phone").toString());

        int id=response.body().path("id");
        String name= response.body().path("name");
        String gender=response.body().path("gender");
        long phone=response.body().path("phone");

        Assert.assertEquals(id, 10);
        Assert.assertEquals(name,"Lorenza");
        Assert.assertEquals(gender,"Female");
        Assert.assertEquals(phone,3312820936l);


    }

    @Test
    public void test2(){

       Response response= RestAssured.get(spartanBaseUrl+"/api/spartans/");

       //extract first id
       int firstId=response.path("id[0]");
        System.out.println(firstId);
       //extract first first name
        String name=response.path("name[0]");
        System.out.println(name);
        //get the last name
        String lastFirstName=response.path("name[-1]");
        System.out.println(lastFirstName);

        //extract all first names
        List<String> names=response.path("name");
        System.out.println(names);
        System.out.println(names.size());
        //extract all phone numbers
        List<Object> allPhoneNumbers=response.path("phone");

        for (Object phoneNumber : allPhoneNumbers) {
            System.out.println(phoneNumber);
        }
    }

    @Test
    public void JsonPath(){

        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().pathParam("id",11)
                .and().get(spartanBaseUrl+"/api/spartans/{id}");
        int id=response.path("id");
        //how to read value with JsonPath
        JsonPath jsonPath= response.jsonPath();
        int jsonId=jsonPath.getInt("id");
        String name=jsonPath.getString("name");
        String gender=jsonPath.getString("gender");
        long phone=jsonPath.getLong("phone");

        Assert.assertEquals(jsonId,11);
        Assert.assertEquals(name,"Nona");
        Assert.assertEquals(gender,"Female");
        Assert.assertEquals(phone,7959094216l);

    }




}
