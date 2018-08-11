package ecommerce.tutorial.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import ecommerce.tutorial.mongodb.models.Category;

public interface CategoryRepository extends MongoRepository<Category, String>
{
    Category findByName(String categoryName);

    @Override
    Optional<Category> findById(String id);
}
