package com.gorest.testsuite;

import com.gorest.propertyreader.PropertyReader;
import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostsExtractionTest extends TestBase {

    static ValidatableResponse response;
    String posts = PropertyReader.getInstance().getProperty("resourcePosts");

    @BeforeClass
    public void extractPosts() {
        Map<String, Integer> queParams = new HashMap<>();
        queParams.put("page", 1);
        queParams.put("per_page", 25);
        response = given()
                .queryParams(queParams)
                .when()
                .get(posts)
                .then().statusCode(200);
    }

    //1. Extract the title
    @Test
    public void test001() {
        List<String> title = response.extract().path("title");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The title is : " + title);
        System.out.println("------------------End of Test---------------------------");
    }
    //2. Extract the total number of record
    @Test
    public void test002() {
        List<String> total = response.extract().path("total");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The total record is : " + total.size());
        System.out.println("------------------End of Test---------------------------");
    }
    //3. Extract the body of 15th record
    @Test
    public void test003() {
        String fifteenthBody = response.extract().path("[14].body");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The body of 15th is : " + fifteenthBody);
        System.out.println("------------------End of Test---------------------------");
    }
    //4. Extract the user_id of all the records
    @Test
    public void test004() {
        List<Integer> listOfIds = response.extract().path("user_id");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All user id's is : " + listOfIds);
        System.out.println("------------------End of Test---------------------------");
    }
    //5. Extract the title of all the records
    @Test
    public void test005() {
        List<String> titleOfRecords = response.extract().path("title");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All titles of records is : " + titleOfRecords);
        System.out.println("------------------End of Test---------------------------");
    }

    //6. Extract the title of all records whose user_id = 5914051
    @Test
    public void test006() {
        List<String> userTitle = response.extract().path("findAll{it.user_id == '5914051'}.title");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The title of 5914051 is : " + userTitle);
        System.out.println("------------------End of Test---------------------------");
    }

    //7. Extract the body of all records whose id = 93822
    @Test
    public void test007() {
        List<String> bodyRecord = response.extract().path("findAll{it.id == '93822'}.body");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The body of 93822 is:  " + bodyRecord);
        System.out.println("------------------End of Test---------------------------");
    }
}
