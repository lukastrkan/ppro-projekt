package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.model.Tag;
import cz.trkan.pproprojekt.repository.TagRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public String listTags(Model model) {
        model.addAttribute("tags", tagRepository.findAll());
        return "tag/list";
    }

    @GetMapping("/create")
    public String createTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "tag/form";
    }

    @PostMapping("/create")
    public String createTag(@Valid Tag tag, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "tag/form";
        }

        //validate unique constraint manually
        if (tagRepository.findByName(tag.getName()) != null) {
            bindingResult.rejectValue("name", "duplicate", "Name already exists");
            model.addAttribute("bindingResult", bindingResult);
            return "tag/form";
        }

        tagRepository.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/edit/{id}")
    public String editTagForm(@PathVariable Long id, Model model) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag Id:" + id));
        model.addAttribute("tag", tag);
        return "tag/form";
    }

    @PostMapping("/edit/{id}")
    public String editTag(@PathVariable Long id, @ModelAttribute Tag tag) {
        tag.setId(id);
        tagRepository.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag Id:" + id));
        tagRepository.delete(tag);
        return "redirect:/tags";
    }
}
