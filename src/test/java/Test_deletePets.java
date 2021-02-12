import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Test_deletePets {

    final static String host = System.getProperty("env.HOME");

    @Severity(SeverityLevel.CRITICAL)
    @Owner("DidenkoAV")
    @Test(priority = 2, description = "Delete pet from service", groups = {"Pet"})
    public void addNewPet() {

        JSONObject requestBody = new JSONObject();
        JSONObject category = new JSONObject();
        ArrayList<String> photos = new ArrayList();
        photos.add(ConfProperties.getProperty("photo"));
        JSONObject tags = new JSONObject();
        ArrayList<JSONObject> tagsObj = new ArrayList<>();
        category.put("id", "2");
        category.put("name", "Russian spaniel");
        tags.put("id", "2");
        tags.put("name", "Marseilles");
        tagsObj.add(tags);
        requestBody.put("id", 0);
        requestBody.put("category", category);
        requestBody.put("name", "Marseilles");
        requestBody.put("photoUrls", photos);
        requestBody.put("tags", tagsObj);
        requestBody.put("status", "avialable");


        RequestSpecification requestAddPet = RestAssured.given().log().all();
        requestAddPet.header("Content-Type", "application/json");
        requestAddPet.body(requestBody.toString());

        ValidatableResponse responseCreatedPet = requestAddPet
                .given()
                .log()
                .all()
                .when()
                .post(host + "/pet")
                .then()
                .statusCode(200)
                .log()
                .all();

        Allure.addAttachment("Endpoint:", "application/json", String.valueOf(" POST " + host + "/pet/"));
        Allure.addAttachment("Add new item, request body:", "application/json", String.valueOf(requestBody));
        Allure.addAttachment("Add new item, response body:", "application/json", String.valueOf(responseCreatedPet.extract().body().asString()));
        Allure.addAttachment("Status code:", "application/json", String.valueOf(responseCreatedPet.extract().statusCode()));

        JsonPath jsonPath = new JsonPath(responseCreatedPet.extract().body().asString());
        String idPet = jsonPath.getString("id");

        RequestSpecification requestForDelete = RestAssured.given().log().all();
        requestForDelete.header("Content-Type", "application/json");
        requestForDelete.body(requestForDelete.toString());

        ValidatableResponse responseForDelete = requestForDelete
                .delete(host + "/pet/" + idPet)
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .all();

        Allure.addAttachment("Endpoint:", "application/json", String.valueOf(" Delete " + host + "/pet/{PetId}" ));
        Allure.addAttachment("Delete item, url","application/json", String.valueOf(" DELETE " + host + "/pet/" + idPet));
        Allure.addAttachment("Delete item, response body","application/json", String.valueOf(responseForDelete.extract().body().asString()));
        Allure.addAttachment("Status code:", "application/json", String.valueOf(responseForDelete.extract().statusCode()));



        RequestSpecification requestCheck = RestAssured.given().log().all();
        requestCheck.header("Content-Type", "application/json");
        requestCheck.body(requestCheck.toString());

        ValidatableResponse responseForCheck = requestCheck
                .get(host + "/pet/" + idPet)
                .then()
                .assertThat()
                .statusCode(404)
                .log()
                .all();

        Allure.addAttachment("Endpoint:", "application/json", String.valueOf(" GET " + host + "/pet/{PetId}" ));
        Allure.addAttachment("Find item, url","application/json", String.valueOf(" Get " + host + "/pet/" + idPet));
        Allure.addAttachment("Find item, response body","application/json", String.valueOf(responseForCheck.extract().body().asString()));
        Allure.addAttachment("Status code:", "application/json", String.valueOf(responseForCheck.extract().statusCode()));






    }

}

