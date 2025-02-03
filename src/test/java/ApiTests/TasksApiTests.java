package ApiTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("Tasks API Tests")
@Feature("Tasks endpoint API tests")
public class TasksApiTests {

    private static final String BASE_URL = "https://baseurl.com";
    private static final String URL_ENDPOINT = "/tasks";
    private static final int TASK_ID = 12345;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Order(1)
    @DisplayName("Create a new Task")
    @Story("Create a Task")
    @Severity(SeverityLevel.CRITICAL)
    void createTask() {
        String TaskJson = """
        {
            "id": 12345,
            "name": "task"
        }
        """;

        var response = given()
                .header("Content-Type", "application/json")
                .body(TaskJson)
                .when()
                .post(URL_ENDPOINT);

        Allure.addAttachment("Create Task Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(TASK_ID))
                .body("name", equalTo("task"));
    }

    @Test
    @Order(2)
    @DisplayName("Get the created Task")
    @Story("Retrieve the task")
    @Severity(SeverityLevel.CRITICAL)
    void getTask() {
        var response = given()
                .when()
                .get(URL_ENDPOINT + "/" + TASK_ID);

        Allure.addAttachment("Get Task Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(TASK_ID))
                .body("name", equalTo("task"));
    }

    @Test
    @Order(3)
    @DisplayName("Update the Task details")
    @Story("Update the task")
    @Severity(SeverityLevel.CRITICAL)
    void updateTask() {
        String updatedTaskJson = """
        {
            "id": 12345,
            "name": "Trenches"
        }
        """;

        var response = given()
                .header("Content-Type", "application/json")
                .body(updatedTaskJson)
                .when()
                .put(URL_ENDPOINT);

        Allure.addAttachment("Update Trenches Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(TASK_ID))
                .body("name", equalTo("Trenches"));
    }

    @Test
    @Order(4)
    @DisplayName("Delete the task")
    @Story("Delete task")
    @Severity(SeverityLevel.CRITICAL)
    void deleteTrenches() {
        var response = given()
                .when()
                .delete(URL_ENDPOINT + "/" + TASK_ID);

        Allure.addAttachment("Delete Trenches Response", "application/json", response.asString());

        response.then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(TASK_ID)));
    }
}
