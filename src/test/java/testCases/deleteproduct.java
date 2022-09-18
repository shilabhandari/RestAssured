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

public class deleteproduct {

	SoftAssert SoftAssert;
	Map<String, String> createpayloadMap;
	Map<String, String> deletepayloadMap;
	String expectedproductName;
	String expectedproductPrice;
	String expectedproductDescription;
	String firstproductId;

	public deleteproduct() {
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
	
	public Map<String, String> deletepayloadMap() {

		deletepayloadMap = new HashMap<String, String>();

		deletepayloadMap.put("id",firstproductId);
		

		return deletepayloadMap;
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
	public void deleteProduct() {

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123")
				.body(deletepayloadMap()).
				when()
				     .delete("/delete.php")
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
     	SoftAssert.assertEquals(actualProductmessage,"Product was deleted.","Product message  not matching!");

		
		SoftAssert.assertAll();

	}

	@Test(priority = 3)
	public void readDeleteProduct() {
		
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
		SoftAssert.assertEquals(actualResponseStatus, 404, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
        SoftAssert.assertEquals(actualResponseContentType, "application/json");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		
		String actualProductmessage = jp.get("messsage");
		SoftAssert.assertEquals(actualProductmessage, "Product does not exist.","Product messages are not matching!");

		SoftAssert.assertAll();

	}

}
