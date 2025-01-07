package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.model.Tag;
import cz.trkan.pproprojekt.repository.TagRepository;
import cz.trkan.pproprojekt.service.ITagService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
public class TagController {

    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public String listTags(Model model) {
        model.addAttribute("tags", tagService.findAll());
        return "tag/list";
    }

    @GetMapping("/create")
    public String createTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        model.addAttribute("title", "Create Tag");
        return "tag/form";
    }

    @PostMapping("/create")
    public String createTag(@Valid Tag tag, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "tag/form";
        }

        //validate unique constraint manually
        if (tagService.findByName(tag.getName()) != null) {
            bindingResult.rejectValue("name", "duplicate", "Name already exists");
            model.addAttribute("bindingResult", bindingResult);
            return "tag/form";
        }

        tagService.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/edit/{id}")
    public String editTagForm(@PathVariable Long id, Model model) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            throw new IllegalArgumentException("Invalid tag Id:" + id);
        }
        model.addAttribute("tag", tag);
        model.addAttribute("title", "Edit Tag");
        return "tag/form";
    }

    @PostMapping("/edit/{id}")
    public String editTag(@PathVariable Long id, @ModelAttribute Tag tag) {
        tag.setId(id);
        tagService.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            throw new IllegalArgumentException("Invalid tag Id:" + id);
        }
        tagService.delete(tag);
        return "redirect:/tags";
    }
}
