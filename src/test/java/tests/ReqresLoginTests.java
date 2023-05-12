package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import model.LoginBodyPojoModel;
import model.LoginResponsePojoModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponseSpec;

public class ReqresLoginTests {
    @Test
    void loginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .log().headers()
                .log().body()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginWithPojoModelTest() {
        LoginBodyPojoModel loginBody = new LoginBodyPojoModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponsePojoModel loginResponse = given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponsePojoModel.class);

//        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
        Assertions.assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

}
