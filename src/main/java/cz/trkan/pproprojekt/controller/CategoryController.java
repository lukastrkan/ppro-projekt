package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.model.Category;
import cz.trkan.pproprojekt.repository.CategoryRepository;
import cz.trkan.pproprojekt.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("title", "Create Category");
        return "category/form";
    }

    @PostMapping("/create")
    public String createCategory(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "category/form";
        }

        //validate unique constraint manually
        if (categoryService.findByName(category.getName()) != null) {
            bindingResult.rejectValue("name", "duplicate", "Name already exists");
            model.addAttribute("bindingResult", bindingResult);
            return "category/form";
        }

        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new IllegalArgumentException("Invalid category Id:" + id);
        }
        model.addAttribute("category", category);
        model.addAttribute("title", "Edit Category");
        return "category/form";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new IllegalArgumentException("Invalid category Id:" + id);
        }
        categoryService.delete(category);
        return "redirect:/categories";
    }
}
