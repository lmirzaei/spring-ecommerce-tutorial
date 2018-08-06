package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.tutorial.jpa.entities.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>
{

}
