package com.target.myretail;

import com.fasterxml.jackson.databind.JsonNode;
import com.target.myretail.models.Prices;
import com.target.myretail.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/products")
@RestController
public class MyRetailController {
    @Autowired
    private PricesRepository repository;

    @GetMapping(value = "/{id}")
    public JsonNode getPrice(@PathVariable("id") String id) {
        Product product = new Product(id);
        Optional<Prices> byId = repository.findById(id);
        byId.ifPresent(product::setPrices);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://redsky.target.com/v2/pdp/tcin/"+ id +"?excludes=taxonomy,promotion,available_to_promise_network,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        JsonNode rawProduct = restTemplate.getForObject(url,JsonNode.class);
        product.parseProduct(rawProduct);
        return product.getJson();
    }
    @PutMapping(value = "/{id}")
    public Prices putPrice(@Valid @RequestBody JsonNode product) {
        JsonNode current_price = product.get("current_price");
        Prices price = new Prices(product.get("id").asText(),current_price.get("value").asText(),current_price.get("currency_code").asText());
        return repository.save(price);
    }
}
