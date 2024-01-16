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

public class UserExtractionTest extends TestBase {
    static ValidatableResponse response;
    String users = PropertyReader.getInstance().getProperty("resourceUser");

    @BeforeClass
    public void extractUser() {
        Map<String, Integer> queParams = new HashMap<>();
        queParams.put("page", 1);
        queParams.put("per_page", 20);
        response = given()
                .queryParams(queParams)
                .when()
                .get(users)
                .then().statusCode(200);
    }

    // 1. Extract the All Ids
    @Test
    public void test001() {
        List<Integer> allIds = response.extract().path("id");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of allIds : " + allIds);
        System.out.println("------------------End of Test---------------------------");
    }

    //2. Extract the all Names
    @Test
    public void test002() {
        List<String> allNames = response.extract().path("name");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All the names are : " + allNames);
        System.out.println("------------------End of Test---------------------------");
    }

    //3. Extract the name of 5th object
    @Test
    public void test003() {
        String fifthName = response.extract().path("[4].name");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The name of 5th object is : " + fifthName);
        System.out.println("------------------End of Test---------------------------");
    }

    //4. Extract the names of all object whose status = inactive
    @Test
    public void test004() {
        List<String> allNamesInactive = response.extract().path("findAll{it.status == 'inactive'}.name");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All the names that are inactive : " + allNamesInactive);
        System.out.println("------------------End of Test---------------------------");
    }

    //5. Extract the gender of all the object hose status = active
    @Test
    public void test005() {
        List<String> genderIsActive = response.extract().path("findAll{it.status == 'active'}.gender");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("Gender that is active : " + genderIsActive);
        System.out.println("------------------End of Test---------------------------");
    }

    //6. Print the names of the object whose gender = female
    @Test
    public void test006() {
        List<String> allNamesFemale = response.extract().path("findAll{it.gender == 'female'}.name");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All the names of females : " + allNamesFemale);
        System.out.println("------------------End of Test---------------------------");
    }

    //7. Get all the emails of the object where status = inactive
    @Test
    public void test007() {
        List<String> inactiveEmails = response.extract().path("findAll{it.status == 'inactive'}.email");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All the inactive emails are : " + inactiveEmails);
        System.out.println("------------------End of Test---------------------------");
    }

    //8. Get the ids of the object where gender = male
    @Test
    public void test008() {
        List<String> maleId = response.extract().path("findAll{it.gender == 'male'}.id");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All the male ID's are : " + maleId);
        System.out.println("------------------End of Test---------------------------");
    }

    //9. Get all the status
    @Test
    public void test009() {
        List<String> maleId = response.extract().path("status");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("All the status : " + maleId);
        System.out.println("------------------End of Test---------------------------");
    }

    //10. Get email of the object where name = Goswami Prajapat
    @Test
    public void test010() {
        List<String> email = response.extract().path("[1].email");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("Email of  : " + email);
        System.out.println("------------------End of Test---------------------------");
    }
    //11. Get gender of id = 5914062
    @Test
    public void test011() {
    List <String> gender = response.extract().path(("findAll{it.id == '5914062'}.gender"));
            System.out.println("------------------StartingTest---------------------------");
            System.out.println("Ids 5914062 : " + gender);
            System.out.println("------------------End of Test---------------------------");
}}