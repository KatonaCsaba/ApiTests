package ApiTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("PetStore API Tests")
@Feature("CRUD Operations on /pet Endpoint")
public class PetStoreApiTests {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PETSTORE_ENDPOINT = "/tasks";
    private static final int DOGE_ID = 12345;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Order(1)
    @DisplayName("Create a new DogeKoin")
    @Story("Create a DogeKoin")
    @Severity(SeverityLevel.CRITICAL)
    void createDogeKoin() {
        String dogeKoinJson = """
        {
            "id": 12345,
            "category": {
                "id": 1,
                "name": "Cryptocurrencies"
            },
            "name": "DogeKoin",
            "photoUrls": [
                "https://upload.wikimedia.org/wikipedia/en/d/d0/Dogecoin_Logo.png"
            ],
            "tags": [
                {
                    "id": 1,
                    "name": "meme"
                }
            ],
            "status": "available"
        }
        """;

        var response = given()
                .header("Content-Type", "application/json")
                .body(dogeKoinJson)
                .when()
                .post(PETSTORE_ENDPOINT);

        Allure.addAttachment("Create DogeKoin Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(DOGE_ID))
                .body("name", equalTo("DogeKoin"))
                .body("status", equalTo("available"));
    }

    @Test
    @Order(2)
    @DisplayName("Get the created DogeKoin")
    @Story("Retrieve a DogeKoin")
    @Severity(SeverityLevel.CRITICAL)
    void getDogeKoin() {
        var response = given()
                .when()
                .get(PETSTORE_ENDPOINT + "/" + DOGE_ID);

        Allure.addAttachment("Get DogeKoin Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(DOGE_ID))
                .body("name", equalTo("DogeKoin"))
                .body("status", equalTo("available"));
    }

    @Test
    @Order(3)
    @DisplayName("Update the DogeKoin details")
    @Story("Update a DogeKoin")
    @Severity(SeverityLevel.CRITICAL)
    void updateDogeKoin() {
        String updatedDogeKoinJson = """
        {
            "id": 12345,
            "category": {
                "id": 1,
                "name": "Cryptocurrencies"
            },
            "name": "DogeKoin Updated",
            "photoUrls": [
                "https://upload.wikimedia.org/wikipedia/en/d/d0/Dogecoin_Logo.png"
            ],
            "tags": [
                {
                    "id": 1,
                    "name": "meme"
                }
            ],
            "status": "trending"
        }
        """;

        var response = given()
                .header("Content-Type", "application/json")
                .body(updatedDogeKoinJson)
                .when()
                .put(PETSTORE_ENDPOINT);

        Allure.addAttachment("Update DogeKoin Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(DOGE_ID))
                .body("name", equalTo("DogeKoin Updated"))
                .body("status", equalTo("trending"));
    }

    @Test
    @Order(4)
    @DisplayName("Get the updated DogeKoin")
    @Story("Retrieve an updated DogeKoin")
    @Severity(SeverityLevel.CRITICAL)
    void getUpdatedDogeKoin() {
        var response = given()
                .when()
                .get(PETSTORE_ENDPOINT + "/" + DOGE_ID);

        Allure.addAttachment("Get Updated DogeKoin Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(DOGE_ID))
                .body("name", equalTo("DogeKoin Updated"))
                .body("status", equalTo("trending"));
    }

    @Test
    @Order(5)
    @DisplayName("Delete the DogeKoin")
    @Story("Delete a DogeKoin")
    @Severity(SeverityLevel.CRITICAL)
    void deleteDogeKoin() {
        var response = given()
                .when()
                .delete(PETSTORE_ENDPOINT + "/" + DOGE_ID);

        Allure.addAttachment("Delete DogeKoin Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(DOGE_ID)));
    }
}
