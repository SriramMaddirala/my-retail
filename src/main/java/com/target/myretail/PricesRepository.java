package com.target.myretail;

import com.target.myretail.models.Prices;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricesRepository extends MongoRepository<Prices,String> {
}
