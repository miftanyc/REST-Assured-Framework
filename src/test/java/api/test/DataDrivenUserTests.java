package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class DataDrivenUserTests {

    List<User> userPayloads;
    Logger logger;

    @BeforeClass
    public void setupData() throws IOException {
        userPayloads = User.xlUserPayloadDataProvider();

        logger = LogManager.getLogger(this.getClass());
    }


   @Test(priority = 1, dataProvider = "Payload")
    public void testPostUser(User userPayload){
       logger.info("********************* Creating User ***********************");
       Response response = UserEndPoints.createUser(userPayload);
       response.then().log().body();

       Assert.assertEquals(response.getStatusCode(), 200);
       Assert.assertEquals(response.jsonPath().getInt("code"), 200);
       Assert.assertEquals(response.jsonPath().get("type"), "unknown");
       Assert.assertEquals(response.jsonPath().getInt("message"), userPayload.getId());
       logger.info("********************* User Created ***********************");

    }


    @Test(priority = 2, dependsOnMethods = "testPostUser", dataProvider = "Payload")
    public void testGetUserByUsername(User userPayload){
        logger.info("********************* Reading User ***********************");
        Response response = UserEndPoints.readUser(userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(userPayload.getId(), response.jsonPath().getInt("id"));
        Assert.assertEquals(userPayload.getUsername(), response.jsonPath().get("username"));
        Assert.assertEquals(userPayload.getFirstName(), response.jsonPath().get("firstName"));
        Assert.assertEquals(userPayload.getLastName(), response.jsonPath().get("lastName"));
        Assert.assertEquals(userPayload.getEmail(), response.jsonPath().get("email"));
        Assert.assertEquals(userPayload.getPassword(), response.jsonPath().get("password"));
        Assert.assertEquals(userPayload.getPhone(), response.jsonPath().get("phone"));
        logger.info("********************* User Info Displayed  ***********************");

    }


    @Test(priority = 3, dependsOnMethods = "testPostUser", dataProvider = "Payload")
    public void testDeleteUserByUsername(User userPayload){
        logger.info("********************* Deleting User ***********************");
        Response response = UserEndPoints.deleteUser(userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("code"), 200);
        Assert.assertEquals(response.jsonPath().get("type"), "unknown");
        Assert.assertEquals(response.jsonPath().get("message"), userPayload.getUsername());
        logger.info("********************* User Deleted ***********************");

    }


    @DataProvider(name="Payload")
    public User[] supplyData(){
        return userPayloads.toArray(new User[0]);
    }

}
