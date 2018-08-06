package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.tutorial.jpa.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{

}
