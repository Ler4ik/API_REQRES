package tests;

import model.CreateBodyModel;
import model.CreateResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import org.assertj.core.api.Assertions;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests {

    @Test
    void createUser(){

        CreateBodyModel loginBody = new CreateBodyModel();
        loginBody.setName("morpheus");
        loginBody.setJob("leader");

        CreateResponseModel loginResponse = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(loginBody)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateResponseModel.class);

        //assertEquals("morpheus",loginResponse.getName());
        Assertions.assertThat(loginResponse.getName()).isEqualTo("morpheus");
    }

    @Test
    void CreateUserWithoutJob(){
        String data = "{ \"name\": \"morpheus\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    void CreateUserWithoutName(){
        String data = "{ \"job\": \"leader\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("job", is("leader"));
    }
}
