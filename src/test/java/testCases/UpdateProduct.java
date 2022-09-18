package testCases;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UpdateProduct {

	SoftAssert SoftAssert;
	Map<String, String> createpayloadMap;
	Map<String, String> updatepayloadMap;
	String expectedproductName;
	String expectedproductPrice;
	String expectedproductDescription;
	String firstproductId;

	public UpdateProduct() {
		SoftAssert = new SoftAssert();
	}

	public Map<String, String> createPayloadMap() {

		createpayloadMap = new HashMap<String, String>();

		createpayloadMap.put("name", "Shila's Amazing Pillow 2.0");
		createpayloadMap.put("price", "599");
		createpayloadMap.put("description", "The best pillow for amazing programmers.");
		createpayloadMap.put("category_id", "2");

		return createpayloadMap;
	}
	
	public Map<String, String> updatePayloadMap() {

		createpayloadMap = new HashMap<String, String>();

		updatepayloadMap.put("id",firstproductId);
		updatepayloadMap.put("name", "Shila's Amazing Pillow 3.0");
		updatepayloadMap.put("price", "899");
		updatepayloadMap.put("description", "The updated pillow for amazing programmers.");
		updatepayloadMap.put("category_id", "2");

		return updatepayloadMap;
	}

	@Test(priority = 0)
	public void createNewProduct() {

		Response response =
				given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8 ").auth().preemptive()
				.basic("demo@techfios.com", "abc123")
				.body(createPayloadMap()).
		   when()
				.post("/create.php").
		   then()
				 .extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		// Assert.assertEquals(actualResponseStatus, 201);
		SoftAssert.assertEquals(actualResponseStatus, 201, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
		// Assert.assertEquals(actualResponseContentType, "application/json");
		SoftAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8",
				"Response Content-Type are not matching!");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String Productmessage = jp.get("message");
		// Assert.assertEquals(ProductId, "4895");
		SoftAssert.assertEquals(Productmessage, "Product was created.", "Product messages are not matching!");

		/*
		 * String productName = jp.get("name");
		 * //Assert.assertEquals(productName,"MD's Amazing Pillow 3.0");
		 * SoftAssert.assertEquals(productName,"shila's Amazing Pillow 3.0",
		 * "Product names are not matching!");
		 * 
		 * String productPrice = jp.get("price"); // Assert.assertEquals(productPrice,
		 * "299"); SoftAssert.assertEquals(productPrice, "299");
		 * System.out.println("product price: " + productPrice);
		 */

		SoftAssert.assertAll();

	}

	/*
	 * .statusCode(200) //.log().all();
	 * .header("Content-Type","application/json; charset=UTF-8")
	 * .statusLine("HTTP/1.1 200 OK");
	 */

//	}
	@Test(priority = 1)
	public void readAllProduct() {
		
		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
				.basic("demo@techfios.com", "abc123"). // .log().all().
				when() // .log().all()
				.get("/read.php").then().extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		SoftAssert.assertEquals(actualResponseStatus, 200);

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
		SoftAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");

		String actualResponseBody = response.getBody().asString();
// System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String firstProductId = jp.get("records[0].id");

		System.out.println("firstProductId: " + firstProductId);
		
		SoftAssert.assertAll();
	}
	
	@Test(priority = 2)
	public void updateProduct() {

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123")
				.body(updatePayloadMap()).
				when()
				     .put("/update.php")
		     	 .then()
				     .extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
//  Assert.assertEquals(actualResponseStatus, 200);
		SoftAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
		SoftAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String actualProductmessage = jp.get("message");
     	SoftAssert.assertEquals(actualProductmessage,"Product was updated.","Product message  not matching!");

		
		SoftAssert.assertAll();

	}

	@Test(priority = 3)
	public void readOneProduct() {
		
		expectedproductName = updatepayloadMap.get("name");
		System.out.println("expected product Name: " + expectedproductName);

		expectedproductPrice = updatepayloadMap.get("price");
		System.out.println("expected product Price: " + expectedproductPrice);

		expectedproductDescription = updatepayloadMap.get("description");
		System.out.println("expected product description: " + expectedproductDescription);

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json")
				.auth().preemptive().basic("demo@techfios.com", "abc123")
				.queryParam("id", "firstproductId")
				.when()
				     .get("/read_one.php")
		     	 .then()
				     .extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		SoftAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
        SoftAssert.assertEquals(actualResponseContentType, "application/json");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String actualProductId = jp.get("id");
     	SoftAssert.assertEquals(actualProductId,"firstproductId","Product Id are not matching!");

		String actualproductName = jp.get("name");
		SoftAssert.assertEquals(actualproductName,expectedproductName,"Product names are not matching!");

		String actualproductPrice = jp.get("price");
		SoftAssert.assertEquals(actualproductPrice,expectedproductPrice,"Product prices are not matching!");
		
		String actualProductDescription = jp.get("description");
		SoftAssert.assertEquals(actualProductDescription,expectedproductDescription,"Product descriptions are not matching!");

		SoftAssert.assertAll();

	}

}
