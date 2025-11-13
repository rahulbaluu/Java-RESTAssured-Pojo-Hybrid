package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.Product;
import pojo.ProductsResponse;

import static org.testng.AssertJUnit.assertNotNull;

public class ProductSteps {
	private Response response;
	private ProductsResponse productsResponse;

	@Given("The API endpoint is available")
	public void theAPIEndpointIsAvailable() {
		RestAssured.baseURI = "https://automationexercise.com/api";
	}

	@When("Send GET request to productsList")
	public void sendGETRequestToProductsList() {
		response = RestAssured.given().when().get("/productsList").then().extract().response();
	}

	@And("Should receive the list of all products")
	public void shouldReceiveTheListOfAllProducts() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		// Deserialize the JSON response into ProductsResponse
		productsResponse = objectMapper.readValue(response.getBody().asString(), ProductsResponse.class);

		// Check that the list itself is not null
		assertNotNull(productsResponse.getProducts().toString(),"Products list should not be null" );

		//Print total number of products
		System.out.println("Total products: " + productsResponse.getProducts().size() + "\n");

		for (Product product : productsResponse.getProducts()) {
			System.out.println("ID:" + product.getId() + "\n" +
					"Name:" + product.getName() + "\n" +
					"Price:" + product.getPrice() + "\n" +
					"Brand:" + product.getBrand());
			if (product.getCategory() != null) {
				System.out.println("Category:" + product.getCategory().getCategory());
				if (product.getCategory().getUsertype()!=null) {
					System.out.println("UserType:" + product.getCategory().getUsertype().getUsertype() + "\n");
				}
			}
		}
	}

	@Then("The status code should be 200")
	public void theStatusCodeShouldBe() {
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
