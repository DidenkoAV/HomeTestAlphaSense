import Pojo.AddPetsPogo;
import Pojo.OrderPojo;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.util.ArrayList;

public class Test_orderPets {
    final static String host = System.getProperty("env.HOME");

    @Severity(SeverityLevel.CRITICAL)
    @Owner("DidenkoAV")
    @Test(priority = 3, description = "Add new pet and book from store side", groups = {"Store"})
    public void addNewPet() {

        JSONObject requestBody = new JSONObject();
        JSONObject category = new JSONObject();
        ArrayList<String> photos = new ArrayList();
        photos.add(ConfProperties.getProperty("photo"));
        JSONObject tags = new JSONObject();
        ArrayList<JSONObject> tagsObj = new ArrayList<>();
        category.put("id", "3");
        category.put("name", "Russian spaniel");
        tags.put("id", "3");
        tags.put("name", "Marseilles");
        tagsObj.add(tags);
        requestBody.put("id", 0);
        requestBody.put("name", "Marseilles");
        requestBody.put("tags", tagsObj);
        requestBody.put("status", "avialable");


        RequestSpecification requestAddPet = RestAssured.given().log().all();
        requestAddPet.header("Content-Type", "application/json");
        requestAddPet.body(requestBody.toString());

        AddPetsPogo addPetsPogo = requestAddPet.post(host + "/pet")
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .all().extract().response().as(AddPetsPogo.class);

        Allure.addAttachment("Endpoint:", "application/json", String.valueOf(" POST " + host + "/pet/"));
        Allure.addAttachment("Add new item, request body:", "application/json", String.valueOf(requestBody));
        Allure.addAttachment("Add new item, response body:", "application/json", String.valueOf(addPetsPogo.toString()));
        Allure.addAttachment("Status:", "application/json", String.valueOf(addPetsPogo.getStatus()));


        String id = addPetsPogo.getId();


        JSONObject requestBodyBookPet = new JSONObject();
        requestBodyBookPet.put("id", id);
        requestBodyBookPet.put("petId", 3);
        requestBodyBookPet.put("quantity", 0);
        requestBodyBookPet.put("shipDate", "2021-02-11T17:30:50.866Z");
        requestBodyBookPet.put("status", "placed");
        requestBodyBookPet.put("complete", true);

        RequestSpecification requestBookPet = RestAssured.given().log().all();
        requestBookPet.header("Content-Type", "application/json");
        requestBookPet.body(requestBodyBookPet.toString());
        OrderPojo orderPojo = requestBookPet
                .queryParam("id",id)
                .queryParam("petId",3)
                .queryParam("quantity",0)
                .queryParam("shipDate","2021-02-11T17:30:50.866Z")
                .queryParam("status","placed")
                .queryParam("complete",true)
                .post(host + "/store/order")
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .response().as(OrderPojo.class);

        System.out.println(orderPojo.toString());

        Allure.addAttachment("Endpoint:", "application/json", String.valueOf(" POST " + host + "/store/order"));
        Allure.addAttachment("Add new book, request body:", "application/json",String.valueOf(requestBodyBookPet.toString()));
        Allure.addAttachment("Add new book, response body:","application/json", String.valueOf(orderPojo.toString()));
        Allure.addAttachment("Status:","application/json", String.valueOf(orderPojo.getStatus()));


    }
}