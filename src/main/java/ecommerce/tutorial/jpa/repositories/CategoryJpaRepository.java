package ecommerce.tutorial.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import ecommerce.tutorial.jpa.entities.CategoryEntity;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long>
{
    List<CategoryEntity> findAllByName(String name);
}
