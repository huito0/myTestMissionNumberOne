package ru.dexsys;

import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class RestfulBookerMethods {

    public String createToken(String userName, String password) {
        JsonObject logPassMap = new JsonObject();
        logPassMap.addProperty("password", password);
        logPassMap.addProperty("username", userName);

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(logPassMap.toString())
                .post(ApiUrls.AUTH_CREATE_TOKEN)
                .then()
                .log().ifValidationFails(LogDetail.ALL)
                .statusCode(200)
                .extract()
                .response()
                .asString();
    }

    public String createBooking() {
        String createBookingBody = "{\n" +
                "    \"firstname\" : \"German\",\n" +
                "    \"lastname\" : \"Konrad\",\n" +
                "    \"totalprice\" : 322,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2020-10-01\",\n" +
                "        \"checkout\" : \"2020-10-15\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast and Dinner\"\n" +
                "}";

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createBookingBody)
                .header("Accept", "application/json")
                .post(ApiUrls.BOOKING_CREATE_BOOKING)
                .then()
                .log().ifValidationFails(LogDetail.ALL)
                .statusCode(200)
                .extract()
                .response()
                .body()
                .jsonPath()
                .getString("bookingid");
    }

    public String getBooking() {
        return RestAssured.given()
                .header("Accept", "application/json")
                .get(ApiUrls.BOOKING_GET_BOOKING + createBooking())
                .then()
                .log().ifValidationFails(LogDetail.ALL)
                .statusCode(200)
                .extract()
                .response()
                .body()
                .jsonPath()
                .getString("firstname");
    }
}

