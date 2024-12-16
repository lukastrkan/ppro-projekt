package cz.trkan.pproprojekt.repository;

import cz.trkan.pproprojekt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
