package ecommerce.tutorial.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ecommerce.tutorial.mongodb.models.Category;

public interface CategoryRepository extends MongoRepository<Category, Long>
{

}
