package ecommerce.tutorial.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ecommerce.tutorial.mongodb.models.Product;

public interface ProductRepository extends MongoRepository<Product, Long>
{

}
