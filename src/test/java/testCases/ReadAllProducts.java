package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {
	
	SoftAssert SoftAssert;
	
	@Test
	public void readAllProduct() {
		/*
		  given: all inputs details(base URL,Headers,Payload/Body,QueryParameters, Authorization)
          when: submit api requests(Http methods,endpoint/resource)
          then:validate response(status code , header,responseTime,playload/body)

    EndPoint_URL:https://techfios.com/api-prod/api/product/read.php
    HTTP Method:Get
    Authorization: basic auth
    Headers/headers:
    Content-Type:application/json; charset=UTF-8
     status code:200
		 */
		SoftAssert = new SoftAssert();
		
Response response = given()
		          .baseUri("https://techfios.com/api-prod/api/product")
		          .header("Content-Type","application/json; charset=UTF-8")
		          .auth().preemptive().basic("demo@techfios.com","abc123").  //.log().all().
		     when()    //.log().all()
		          .get("/read.php").
	         then()
		          .extract().response();

       int actualResponseStatus =  response.getStatusCode();
       System.out.println("actual Response Status: " + actualResponseStatus );
       SoftAssert.assertEquals(actualResponseStatus, 200);
       
   String actualResponseContentType = response.getHeader("Content-Type");
   System.out.println("actual Response ContentType: " + actualResponseContentType);
   SoftAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");
   
  String actualResponseBody =  response.getBody().asString();
  System.out.println("actualResponseBody: " + actualResponseBody);
  
  JsonPath jp = new JsonPath(actualResponseBody);
  String firstProductId  =jp.get("records[0].id");
  
  if(firstProductId != null) {
	  System.out.println("Product exist.");
	  }else {
		  System.out.println("Product does not exist!");
	  }


/*.statusCode(200)   //.log().all();
		          .header("Content-Type","application/json; charset=UTF-8")
		          .statusLine("HTTP/1.1 200 OK");*/
		     
		     
	}

	

}
