package training;

import models.Product;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test
    public void getCategories()
    {
        String endpoint = "http://localhost:8888/api_testing/category/read.php";
        var response = given().when().get(endpoint).then();
        response.log().body();

    }
    @Test
    public void getProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        var response =
                        given().
                                queryParam("id", 2).
                        when().
                               get(endpoint).
                        then();
        response.log().body();



    }
    @Test
    public void createProduct()
    {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        String body = """
                {
                "name" : "Water Bottle",
                "description":"Blue water bottle. Holds 64 ounces",
                "price": 1,
                "category_id":3
                }
                """;
        var response = given().
                                                 body(body).
                                         when().
                                                post(endpoint).
                                         then();
        response.log().body();
    }
    @Test
    public void updateProduct()
    {
      String endpoint = "http://localhost:8888/api_testing/product/update.php";
      String body = """
              {
              "id": 19,
              "name":"Big Water Bottle",
              "description":"Big Blue water bottle.6 inches",
              "price":15,
              "category_id":4
              }
              """;
      var response= given().
                                              body(body).
                                       when().
                                               put(endpoint).
                                       then();
      response.log().body();
    }
    @Test
    public void deleteProduct()
    {
        String endpoint = "http://localhost:8888/api_testing/product/delete.php" ;
        String body = """
                "id":19
                """;
        var response = given().
                                                 body(body).
                                         when().
                                                delete(endpoint).
                                         then();
        response.log().body();

    }
    @Test
    public void createSerializedProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product("Water Bottle", "Blue water bottle", 12, 3);
        var response = given().body(product).when()
                .post(endpoint).then();
        response.log().body();
    }
    @Test
    public void createSweatbandProduct()
    {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Sweatband",
                "Sweatband for head.",
                5,
                3);
        var response = given()
                                                .body(product).
                                         when()
                                                .post(endpoint).
                                         then();
        response.log().body();

    }
    @Test
    public void updateSweatbandProduct()
    {
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        String body = """
                {
                "id":22,
                "price":6
                }
                """;
        var response = given().
                                                 body(body).when().put(endpoint).then();
        response.log().body();
    }
    @Test
    public void getSweatband(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        var response = given().
                                                queryParam("id", 22).
                                        when().
                                                get(endpoint).
                                        then().
                                                assertThat().
                                                statusCode(200).
                                                body("name", equalTo("Sweatband"));

    }

    @Test
    public void getComplexResponse(){
        String endpoint = "http://localhost:8888/api_testing/product/read.php";
        var response = given().
                                         when().
                                                get(endpoint).
                                         then().
                                                    assertThat().
                                                    statusCode(200).
                                                    body("records.size()", greaterThan(22)).
                                                    body("records.name", everyItem(notNullValue())).
                                                    body("records.id[0]",equalTo("26"));

        response.log().body();

    }


}
