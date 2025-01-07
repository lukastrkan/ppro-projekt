package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {
    List<Category> findAll();

    Category findByName(String name);

    Category findById(Long id);

    void save(Category category);

    void delete(Category category);
}
