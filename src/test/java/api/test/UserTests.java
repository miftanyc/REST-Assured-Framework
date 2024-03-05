package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;

import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



public class UserTests {

    Faker faker = new Faker();
    User userPayload;
    Logger logger;

    @BeforeClass
    public void setupData(){
        userPayload = User.userPayloadGenerator();

        //logs
        logger = LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser(){
        logger.info("********************* Creating User ***********************");
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().body();


        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("code"), 200);
        Assert.assertEquals(response.jsonPath().get("type"), "unknown");
        Assert.assertEquals(response.jsonPath().getInt("message"), userPayload.getId());
        logger.info("********************* User Created ***********************");

    }

    @Test(priority = 2, dependsOnMethods = "testPostUser")
    public void testGetUserByUsername(){
        logger.info("********************* Reading User ***********************");
        Response response = UserEndPoints.readUser(this.userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(userPayload.getId(), response.jsonPath().getInt("id"));
        Assert.assertEquals(userPayload.getUsername(), response.jsonPath().get("username"));
        Assert.assertEquals(userPayload.getFirstName(), response.jsonPath().get("firstName"));
        Assert.assertEquals(userPayload.getLastName(), response.jsonPath().get("lastName"));
        Assert.assertEquals(userPayload.getEmail(), response.jsonPath().get("email"));
        Assert.assertEquals(userPayload.getPassword(), response.jsonPath().get("password"));
        Assert.assertEquals(userPayload.getPhone(), response.jsonPath().get("phone"));
        logger.info("********************* User Info Displayed  ***********************");

    }

    @Test(priority = 3, dependsOnMethods = "testPostUser")
    public void testUpdateUserByUsername(){
        logger.info("********************* Updating User ***********************");
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        Response updateResponse = UserEndPoints.updateUser(this.userPayload, userPayload.getUsername());
        updateResponse.then().log().body();

        //Get The updated User
        Response updatedGetResponse = UserEndPoints.readUser(this.userPayload.getUsername());
        updatedGetResponse.then().log().body();
        logger.info("********************* User Updated ***********************");

    }

    @Test(priority = 4, dependsOnMethods = "testPostUser")
    public void testDeleteUserByUsername(){
        logger.info("********************* Deleting User ***********************");
        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("code"), 200);
        Assert.assertEquals(response.jsonPath().get("type"), "unknown");
        Assert.assertEquals(response.jsonPath().get("message"), userPayload.getUsername());
        logger.info("********************* User Deleted ***********************");

    }

}
