package com.target.myretail;

import com.fasterxml.jackson.databind.JsonNode;
import com.target.myretail.models.Prices;
import com.target.myretail.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class MyRetailController {
    @Autowired
    private PricesRepository repository;
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("")
    public @ResponseBody String greeting() {
        return "Welcome to the MyRetail Products API. " +
                "To use this service append /products/{id} to the url with a get/put request.";
    }

    @RequestMapping("/about")
    public @ResponseBody String about() {
        return "Author : Ram Maddirala. " +
                "This Service was built with Spring Boot, connects with MongoDB " +
                "and is containerized with Docker and hosted on Google Cloud Run.";
    }

    /**
     * This method gets the price of a products from MongoDB and combines it
     * with the name from the target redsky.target API and returns the information in a JsonNode.
     * In the case that there is no Product Price in the DB, it gets the pricing information
     * from redsky.target and puts the pricing information into the DB for future iterations.
     * @param id : This is the id of the product for which details are requested.
     * @return JsonNode : This is the JsonNode containing Product Details.
     */
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public JsonNode getPrice(@PathVariable("id") String id) {
        Product product = new Product(id);
        Optional<Prices> byId = repository.findById(id);
        // If Product price is present in the DB, let's put that into the response.
        byId.ifPresent(product::setPrices);
        String url = "https://redsky.target.com/v2/pdp/tcin/"+ id +"?excludes=taxonomy,promotion,available_to_promise_network,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        JsonNode rawProduct = restTemplate.getForObject(url,JsonNode.class);
        // If product price isn't in the DB, let's get the price from the external API
        // as there is a one-to-one mapping between products and prices.
        product.parseProduct(rawProduct);
        if(!product.isPriceFromDB()){
            // If product price isn't in the DB, let's put it there.
            repository.save(product.getPrices());
        }
        return product.getJson();
    }

    /**
     * This method updates the price of a product in the DB.
     * If there is no such product price in the DB it throws an illegalArgument Exception
     * @param id : This is the id of the product for which details are requested.
     * @param product : This is the JsonNode containing Product Details.
     * @return Prices: It returns the product price details that have been saved into the DB.
     */
    @PutMapping(value = "/products/{id}", produces = "application/json")
    public Prices updatePrice(@PathVariable("id") String id, @Valid @RequestBody JsonNode product) {
        if(!product.has("id")){
            throw new IllegalArgumentException("No product id in the Product details request.");
        }
        if(!product.has("current_price")){
            throw new IllegalArgumentException("No current_price in the Product details request.");
        }

        JsonNode current_price = product.get("current_price");

        if(!current_price.has("value")){
            throw new IllegalArgumentException("No value in Current Price.");
        }
        if(!current_price.has("currency_code")){
            throw new IllegalArgumentException("No currency code in Current Price.");
        }
        if (!product.get("id").asText().equals(id)) {
            throw new IllegalArgumentException("Path Variable Id doesn't match Product ID that is being put into the Data Store");
        }
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("This product ID doesn't exist in the Data Store.");
        }

        Prices price = new Prices(product.get("id").asText(), current_price.get("value").asText(), current_price.get("currency_code").asText());
        return repository.save(price);
    }

    protected void setRepository(PricesRepository mockRepository, RestTemplate mockTemplate){
        repository = mockRepository;
        restTemplate = mockTemplate;
    }
}
