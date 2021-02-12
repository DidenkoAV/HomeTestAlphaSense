import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Test_broken {

    final static String host =  System.getProperty("env.HOME");

    @Severity(SeverityLevel.TRIVIAL)
    @Owner("DidenkoAV")
    @Test(priority = 4, description = "Test has broken")
    public void addNewPet(){

        //Prepare test data for request
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
        requestBody.put("category", category);
        requestBody.put("name", "Marseilles");
        requestBody.put("photoUrls", photos);
        requestBody.put("tags", tagsObj);
        requestBody.put("status", "avialable");


        RequestSpecification requestAddPet = RestAssured.given().log().all();
        requestAddPet.header("Content-Type", "application/json");
        requestAddPet.body(requestBody.toString());
        String responseCreatedPet = requestAddPet.post(host + "/pet/unknown")
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .response()
                .asString();
        Allure.addAttachment("Request body for endpoint /pet , add new item", String.valueOf(requestBody.toString()));
        Allure.addAttachment("Response body from endpoint /pet", String.valueOf(responseCreatedPet));
}

}