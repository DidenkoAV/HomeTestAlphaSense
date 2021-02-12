import Pojo.AddPetsPogo;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import io.qameta.allure.*;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.util.ArrayList;


public class Test_addPets {

    final static String host = System.getProperty("env.HOME");

    @Severity(SeverityLevel.CRITICAL)
    @Links(value = {@Link(name = "/pet", url = "https://petstore.swagger.io/#/pet/addPet"), @Link(name = "/pet/{petId}", url = "https://petstore.swagger.io/#/pet/getPetById")})
    @Owner("DidenkoAV")
    @Test(priority = 1, description = "Add new pet and check result", groups = {"Pet"})
    @Step("Add new pet and check")
    public void addNewPet(){

        JSONObject requestBody = new JSONObject();
        JSONObject category = new JSONObject();
        ArrayList<String> photos = new ArrayList();
        photos.add(ConfProperties.getProperty("photo"));
        JSONObject tags = new JSONObject();
        ArrayList<JSONObject> tagsObj = new ArrayList<>();
        category.put("id", "1");
        category.put("name", "Russian spaniel");
        tags.put("id","1");
        tags.put("name", "Marseilles");
        tagsObj.add(tags);
        requestBody.put("id",0);
        requestBody.put("name", "Marseilles");
        requestBody.put("tags", tagsObj);
        requestBody.put("status", "avialable");


        RequestSpecification requestAddPet = RestAssured.given().log().all();
        requestAddPet.header("Content-Type", "application/json");
        requestAddPet.body(requestBody.toString());

        AddPetsPogo addPetsPogo = requestAddPet
                .given()
                .log()
                .all()
                .when()
                .post(host + "/pet")
                .then()
                .statusCode(200)
                .log()
                .all().extract().response().as(AddPetsPogo.class);

            Allure.addAttachment("Endpoint:", "application/json", String.valueOf(" POST " + host + "/pet/"));
            Allure.addAttachment("Add new item, request body:", "application/json", String.valueOf(requestBody));
            Allure.addAttachment("Add new item, response body:", "application/json", String.valueOf(addPetsPogo.toString()));
            Allure.addAttachment("Status:", "application/json", String.valueOf(addPetsPogo.getStatus()));

        String idPet = addPetsPogo.getId();
        RequestSpecification requestCheck = RestAssured.given().log().all();
        requestCheck.header("Content-Type", "application/json");
        requestCheck.body(requestCheck.toString());

        ValidatableResponse responseForCheck = requestCheck
                .get(host + "/pet/" + idPet)
                .then()
                .statusCode(200)
                .log()
                .all();

        Allure.addAttachment("Endpoint:", "application/json",   String.valueOf(" GET " + host + "/pet/{dPet}"));
        Allure.addAttachment("Check item, request body", "application/json",   String.valueOf(" GET " + host + "/pet/" + idPet));
        Allure.addAttachment("Check item, response body", "application/json", String.valueOf(responseForCheck.extract().body().asString()));
        Allure.addAttachment("Status code:", "application/json", String.valueOf(responseForCheck.extract().statusCode()));



    }
}
