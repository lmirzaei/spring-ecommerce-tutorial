package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.tutorial.jpa.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{

}
