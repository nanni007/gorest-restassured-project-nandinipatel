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

public class UserAssertionTest extends TestBase {
    static ValidatableResponse response;
    String users = PropertyReader.getInstance().getProperty("resourceUser");

    @BeforeClass
    public void assertUsers() {
        Map<String, Integer> queParams = new HashMap<>();
        queParams.put("page", 1);
        queParams.put("per_page", 20);
        response = given()
                .queryParams(queParams)
                .when()
                .get(users)
                .then().statusCode(200);
    }

    //1. Verify if the total record is 20
    @Test
    public void test001() {
        List<Map<String,?>>list=response.extract().path("$");
        Assert.assertEquals(list.size(),20);
    }
    //2. Verify if the name of id = 5914072 is equal to ”Daiwik Iyer”
    @Test
    public void test002() {
       response.body("findAll{it.id==5914072}.get(0).name",equalTo("Daiwik Iyer"));
    }
    //3. Check the single ‘Name’ in the Array list (Goswami Prajapat)
    @Test
    public void test003() {
        response.body("name",hasItem("Goswami Prajapat"));
    }

    //4. Check the multiple ‘Names’ in the ArrayList ("Dr. Vishwamitra Bhattacharya","Pres. Bhagvan Deshpande","Somu Bhat" )
    @Test
    public void test004() {
        response.body("name",hasItems("Dr. Vishwamitra Bhattacharya","Pres. Bhagvan Deshpande","Somu Bhat"));
    }

    //5. Verify the email of userid = 5914071 is equal “dubashi_bhishma@kemmer-dickens.test”
    @Test
    public void test005() {
        response.body("findAll{it.id==5914071}.get(0).email",equalTo("dubashi_bhishma@kemmer-dickens.test"));
    }

    //6. Verify the status is “Active” of username is “Abhaya Mahajan I”
    @Test
    public void test006() {
        response.body("findAll{it.name=='Abhaya Mahajan I'}.get(0).status",equalTo("active"));
    }

    //7. Verify the Gender = male of user name is "Rev. Brahmaanand Khanna"
    @Test
    public void test007() {
        response.body("findAll{it.name=='Rev. Brahmaanand Khanna'}.get(0).gender",equalTo("female"));
    }
}
