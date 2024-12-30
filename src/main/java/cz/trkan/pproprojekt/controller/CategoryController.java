package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.model.Category;
import cz.trkan.pproprojekt.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "category/list";
    }

    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @PostMapping("/create")
    public String createCategory(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "category/form";
        }

        //validate unique constraint manually
        if (categoryRepository.findByName(category.getName()) != null) {
            bindingResult.rejectValue("name", "duplicate", "Name already exists");
            model.addAttribute("bindingResult", bindingResult);
            return "category/form";
        }

        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "category/form";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        categoryRepository.delete(category);
        return "redirect:/categories";
    }
}
