package ecommerce.tutorial.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

import ecommerce.tutorial.mongodb.models.Seller;

public interface SellerRepository extends MongoRepository<Seller, String>
{
    @Query("{'profile.firstName': ?0}")
    List<Seller> findByFirstName(String firstName);

    @Override
    Optional<Seller> findById(String s);
}
