package testCases;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadOneProduct {
	
	SoftAssert SoftAssert;
	
	public ReadOneProduct() {
		SoftAssert = new SoftAssert();
	}
	
	
	
	@Test
	public void readOneProduct() {
		
		
		/*
		  given: all inputs details(base URL,Headers,Payload/Body,QueryParameters, Authorization)
          when: submit api requests(Http methods,endpoint/resource)
          then:validate response(status code , header,responseTime,playload/body)

 EndPoint_Url:https://techfios.com/api-prod/api/product/read_one.php
  HTTP Method:Get
Authorization: basic auth
QueryParameters:id=3976
Headers/headers:
Content_Type:application/json; charset=UTF-8
status code:200 
*/
		
Response response = 
                given()
                 //  .log().all()

		          .baseUri("https://techfios.com/api-prod/api/product")
		          .header("Content-Type","application/json")
		          .auth().preemptive().basic("demo@techfios.com","abc123")
		          .queryParam("id", "4895").
		     when()  
		     // .log().all()
		          .get("/read_one.php").
	         then()
	        // .log().all()
		          .extract().response();

       int actualResponseStatus =  response.getStatusCode();
       System.out.println("actual Response Status: " + actualResponseStatus );
     //  Assert.assertEquals(actualResponseStatus, 200);
       SoftAssert.assertEquals(actualResponseStatus, 200,"Status codes are not matching!");
       
   String actualResponseContentType = response.getHeader("Content-Type");
   System.out.println("actual Response ContentType: " + actualResponseContentType);
  // Assert.assertEquals(actualResponseContentType, "application/json");
   SoftAssert.assertEquals(actualResponseContentType, "application/json");
   
  String actualResponseBody =  response.getBody().asString();
  System.out.println("actualResponseBody: " + actualResponseBody);
  
 JsonPath jp = new JsonPath(actualResponseBody);
  String ProductId  =jp.get("id");
 // Assert.assertEquals(ProductId, "4895");
  SoftAssert.assertEquals(ProductId, "4895", "Product Id are not matching!");
  
  String productName = jp.get("name");
  //Assert.assertEquals(productName,"Shila's Amazing Pillow 2.0");
  SoftAssert.assertEquals(productName,"Shila's Amazing Pillow 2.0", "Product names are not matching!");
  
  String productPrice = jp.get("price");
 // Assert.assertEquals(productPrice, "299");
  SoftAssert.assertEquals(productPrice, "99");
  System.out.println("product price: " + productPrice);
  
  SoftAssert.assertAll();

	  }


/*.statusCode(200)   //.log().all();
		          .header("Content-Type","application/json; charset=UTF-8")
		          .statusLine("HTTP/1.1 200 OK");*/
		     
		     
//	}

	

}
