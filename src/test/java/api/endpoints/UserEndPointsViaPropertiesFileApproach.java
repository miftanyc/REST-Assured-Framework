package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class UserEndPointsViaPropertiesFileApproach {

    static ResourceBundle routes;

    static {
        routes = ResourceBundle.getBundle("routes");// File name for Properties File in Resource Folder
    }

    public static Response createUser(User payload){
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(payload)

                        .when()
                        .post(routes.getString("post_url"));

        return response;
    }

    public static Response readUser(String userName){
        Response response =
                given()
                        .accept(ContentType.JSON)
                        .pathParam("username", userName)

                        .when()
                        .get(routes.getString("get_url"));

        return response;
    }

    public static Response updateUser(User payload, String userName){
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .pathParam("username", userName)
                        .body(payload)

                        .when()
                        .put(routes.getString("update_url"));

        return response;
    }

    public static Response deleteUser(String userName){
        Response response =
                given()
                        .accept(ContentType.JSON)
                        .pathParam("username", userName)

                        .when()
                        .delete(routes.getString("delete_url"));

        return response;
    }
}
