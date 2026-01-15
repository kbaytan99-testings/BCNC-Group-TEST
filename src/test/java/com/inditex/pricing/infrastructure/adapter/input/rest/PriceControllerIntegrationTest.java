package com.inditex.pricing.infrastructure.adapter.input.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for the Price REST API endpoint.
 * 
 * These tests validate the 5 scenarios specified in the requirements:
 * - Test 1: Request at 10:00 on the 14th for product 35455 and brand 1 (ZARA)
 * - Test 2: Request at 16:00 on the 14th for product 35455 and brand 1 (ZARA)
 * - Test 3: Request at 21:00 on the 14th for product 35455 and brand 1 (ZARA)
 * - Test 4: Request at 10:00 on the 15th for product 35455 and brand 1 (ZARA)
 * - Test 5: Request at 21:00 on the 16th for product 35455 and brand 1 (ZARA)
 * 
 * Uses REST Assured for clean and readable API testing.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    private static final String BASE_PATH = "/api/prices";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    @DisplayName("Test 1: Request at 10:00 on June 14, 2020 - Should return price list 1 with price 35.50")
    void testPriceAt10AmOn14th() {
        given()
            .queryParam("applicationDate", "2020-06-14T10:00:00")
            .queryParam("productId", PRODUCT_ID)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("productId", equalTo(PRODUCT_ID.intValue()))
            .body("brandId", equalTo(BRAND_ID.intValue()))
            .body("priceList", equalTo(1))
            .body("startDate", equalTo("2020-06-14-00.00.00"))
            .body("endDate", equalTo("2020-12-31-23.59.59"))
            .body("price", equalTo(35.50f))
            .body("currency", equalTo("EUR"));
    }

    @Test
    @DisplayName("Test 2: Request at 16:00 on June 14, 2020 - Should return price list 2 with price 25.45")
    void testPriceAt4PmOn14th() {
        given()
            .queryParam("applicationDate", "2020-06-14T16:00:00")
            .queryParam("productId", PRODUCT_ID)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("productId", equalTo(PRODUCT_ID.intValue()))
            .body("brandId", equalTo(BRAND_ID.intValue()))
            .body("priceList", equalTo(2))
            .body("startDate", equalTo("2020-06-14-15.00.00"))
            .body("endDate", equalTo("2020-06-14-18.30.00"))
            .body("price", equalTo(25.45f))
            .body("currency", equalTo("EUR"));
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on June 14, 2020 - Should return price list 1 with price 35.50")
    void testPriceAt9PmOn14th() {
        given()
            .queryParam("applicationDate", "2020-06-14T21:00:00")
            .queryParam("productId", PRODUCT_ID)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("productId", equalTo(PRODUCT_ID.intValue()))
            .body("brandId", equalTo(BRAND_ID.intValue()))
            .body("priceList", equalTo(1))
            .body("startDate", equalTo("2020-06-14-00.00.00"))
            .body("endDate", equalTo("2020-12-31-23.59.59"))
            .body("price", equalTo(35.50f))
            .body("currency", equalTo("EUR"));
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on June 15, 2020 - Should return price list 3 with price 30.50")
    void testPriceAt10AmOn15th() {
        given()
            .queryParam("applicationDate", "2020-06-15T10:00:00")
            .queryParam("productId", PRODUCT_ID)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("productId", equalTo(PRODUCT_ID.intValue()))
            .body("brandId", equalTo(BRAND_ID.intValue()))
            .body("priceList", equalTo(3))
            .body("startDate", equalTo("2020-06-15-00.00.00"))
            .body("endDate", equalTo("2020-06-15-11.00.00"))
            .body("price", equalTo(30.50f))
            .body("currency", equalTo("EUR"));
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on June 16, 2020 - Should return price list 4 with price 38.95")
    void testPriceAt9PmOn16th() {
        given()
            .queryParam("applicationDate", "2020-06-16T21:00:00")
            .queryParam("productId", PRODUCT_ID)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("productId", equalTo(PRODUCT_ID.intValue()))
            .body("brandId", equalTo(BRAND_ID.intValue()))
            .body("priceList", equalTo(4))
            .body("startDate", equalTo("2020-06-15-16.00.00"))
            .body("endDate", equalTo("2020-12-31-23.59.59"))
            .body("price", equalTo(38.95f))
            .body("currency", equalTo("EUR"));
    }

    @Test
    @DisplayName("Test: Request with invalid date - Should return 400 Bad Request")
    void testInvalidDateFormat() {
        given()
            .queryParam("applicationDate", "invalid-date")
            .queryParam("productId", PRODUCT_ID)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("error", equalTo("Bad Request"));
    }

    @Test
    @DisplayName("Test: Request with non-existent product - Should return 404 Not Found")
    void testNonExistentProduct() {
        given()
            .queryParam("applicationDate", "2020-06-14T10:00:00")
            .queryParam("productId", 99999L)
            .queryParam("brandId", BRAND_ID)
        .when()
            .get()
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", equalTo("Price Not Found"));
    }

    @Test
    @DisplayName("Test: Request without required parameters - Should return 400 Bad Request")
    void testMissingParameters() {
        given()
            .queryParam("applicationDate", "2020-06-14T10:00:00")
        .when()
            .get()
        .then()
            .statusCode(400);
    }
}
