package testCases;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class CreateNewProduct {

	SoftAssert SoftAssert; 

	Map<String, String> payloadMap;
	String expectedproductName;
	String expectedproductPrice;
	String expectedproductDescription;
	String firstproductId;

	public CreateNewProduct() {
		SoftAssert = new SoftAssert();
	}

	public Map<String, String> createPayloadMap() {

		payloadMap = new HashMap<String, String>();

		payloadMap.put("name", "Shila's Amazing Pillow 2.0");
		payloadMap.put("price", "599");
		payloadMap.put("description", "The best pillow for amazing programmers.");
		payloadMap.put("category_id", "2");

		return payloadMap;
	}

	@Test(priority = 0)
	public void createNewProduct() {

		/*
		 * given: all inputs details(base URL,Headers,Payload/Body,QueryParameters,
		 * Authorization) when: submit api requests(Http methods,Endpoint/resource)
		 * then:validate response(status code , header,responseTime,playload/body)
		 * 
		 * EndPoint_URL:https://techfios.com/api-prod/api/product/create.php HTTP
		 * Method:Post Authorization: basic auth Headers/headers:
		 * Content_Type:application/json; charset=UTF-8 status code:200 body/payload: {
		 * "name": MD's Amazing Pillow 2.0", "price": 199, "description" :
		 * "The best pillow for amazing programmers.", "category_id": "2" }
		 */

		Response response = given()
				// .log().all()

				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8 ").auth().preemptive()
				.basic("demo@techfios.com", "abc123")
				// .body(new File("src\\main\\java\\data\\CreatePayload.json")).
				.body(createPayloadMap()).
				when()
				// .log().all()
				.post("/create.php").then()
				// .log().all()
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
		
		expectedproductName = payloadMap.get("name");
		System.out.println("expected product Name: " + expectedproductName);

		expectedproductPrice = payloadMap.get("price");
		System.out.println("expected product Price: " + expectedproductPrice);

		expectedproductDescription = payloadMap.get("description");
		System.out.println("expected product description: " + expectedproductDescription);

		System.out.println("expectedproductName inside readAllProduct:" + expectedproductName);

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
		String firstproductId = jp.get("records[0].id");

		System.out.println("firstproductId: " + firstproductId);
		
		SoftAssert.assertAll();
	}

	@Test(priority = 2)
	public void readOneProduct() {

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json").auth().preemptive().basic("demo@techfios.com", "abc123")
				.queryParam("id", "firstproductId").when().get("/read_one.php").then().extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
//  Assert.assertEquals(actualResponseStatus, 200);
		SoftAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
// Assert.assertEquals(actualResponseContentType, "application/json");
		SoftAssert.assertEquals(actualResponseContentType, "application/json");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String actualproductId = jp.get("id");
// Assert.assertEquals(ProductId, "4895");
		SoftAssert.assertEquals(actualproductId,"firstproductId","Product Id are not matching!");

		String actualproductName = jp.get("name");
		SoftAssert.assertEquals(actualproductName, expectedproductName,"Product names are not matching!");

		String actualproductPrice = jp.get("price");
		SoftAssert.assertEquals(actualproductPrice, expectedproductPrice,"Product prices are not matching!");
		

		SoftAssert.assertAll();

	}

}
