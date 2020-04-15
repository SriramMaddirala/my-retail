package com.target.myretail;

import com.fasterxml.jackson.databind.JsonNode;
import com.target.myretail.models.Prices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/products")
@RestController
public class MyRetailController {
    @Autowired
    private PricesRepository repository;

    @GetMapping(value = "/{id}")
    public Prices getPrice(@PathVariable("id") String id) {
        Optional<Prices> byId = repository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return byId.get();
    }
    @PutMapping(value = "/{id}")
    public Prices putPrice(@Valid @RequestBody JsonNode product) {
        JsonNode current_price = product.get("current_price");
        Prices price = new Prices(product.get("id").asText(),current_price.get("value").asText(),current_price.get("currency_code").asText());
        return repository.save(price);
    }
}
