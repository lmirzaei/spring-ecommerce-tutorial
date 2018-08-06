package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.tutorial.jpa.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>
{

}
