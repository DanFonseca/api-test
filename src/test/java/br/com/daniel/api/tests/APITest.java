package br.com.daniel.api.tests;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APITest {

	@BeforeClass
	public static void setup () {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas () {
		RestAssured
		.given()
		.when().get("/todo")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void deveAdicionarUmaTarefa() {
		String myjson = "{\"task\": \"teste\", \"dueDate\": \"2020-10-10\"}";
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(myjson)
		.when()
		.post("/todo")
		.then()
		.statusCode(201);
	}
	
	@Test
	public void deveRecusarDataPassada () {
		String myjson = "{\"task\": \"teste\", \"dueDate\": \"2010-10-10\"}";
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(myjson)
		.when()
		.post("/todo")
		.then()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"));
	}
	
	@Test
	public void deveDeletarTask () {
		String myjson = "{\"task\": \"teste to delete\", \"dueDate\": \"2030-10-10\"}";
		
		Response response = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(myjson)
		.when()
		.post("/todo")
		.then()
		.statusCode(201)
		.extract().response();
		
		JsonPath jsonPath = response.jsonPath();
		int id = jsonPath.getInt("id");
		System.out.println(id);
		
		//deleting
		RestAssured.given()
		.when()
		.delete("todo/"+id)
		.then()
		.statusCode(204);
	
	}
}
	  
