package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.tutorial.jpa.entities.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, Long>
{

}
