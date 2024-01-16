package com.gorest.testsuite;

import com.gorest.propertyreader.PropertyReader;
import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostsAssertionTest extends TestBase {
    static ValidatableResponse response;
    String posts = PropertyReader.getInstance().getProperty("resourcePosts");

    @BeforeClass
    public void assertPosts() {
        Map<String, Integer> queParams = new HashMap<>();
        queParams.put("page", 1);
        queParams.put("per_page", 25);
        response = given()
                .queryParams(queParams)
                .when()
                .get(posts)
                .then().statusCode(200);
    }
    //1. Verify if the total record is 25
    @Test
    public void test001() {
        List<Map<String,?>> list=response.extract().path("$");
        Assert.assertEquals(list.size(),25);
    }
    //2. Verify if the title of id = 5914181 is equal to ”Demitto conqueror atavus argumentum corrupti cohaero libero.”
    @Test
    public void test002() {
        response.body("findAll{it.user_id==5914181}.get(0).title",equalTo("Ambitus thesaurus contabesco tero amplitudo confugo et tutamen vulgivagus."));
    }
    //3. Check the single user_id in the Array list (5914161)
    @Test
    public void test003(){
        response.body("user_id",hasItem(5914181));
    }

    //4. Check the multiple ids in the ArrayList (5914161, 5914181, 5914151)
    @Test
    public void test004(){
        response.body("user_id",hasItems(5914161,5914181,5914151));
    }

    //5. Verify the body of userid = 5914184 is equal "Cinis non solum. Decretum auctus artificiose. Bos umerus totam. Sed cicuta debitis. Crur unde tum. Et tabella dignissimos. Cognomen vito bardus. Deduco ara una. Desparatus amet caste. Quis taedium sollers."
    @Test
    public void test005() {
        response.body("findAll{it.user_id==5914184}.get(0).body",equalTo("Cinis non solum. Decretum auctus artificiose. Bos umerus totam. Sed cicuta debitis. Crur unde tum. Et tabella dignissimos. Cognomen vito bardus. Deduco ara una. Desparatus amet caste. Quis taedium sollers."));
    }

}
