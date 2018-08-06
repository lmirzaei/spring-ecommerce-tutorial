package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.tutorial.jpa.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>
{

}
