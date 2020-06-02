package br.com.daniel.api.tests;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

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
}
	  
