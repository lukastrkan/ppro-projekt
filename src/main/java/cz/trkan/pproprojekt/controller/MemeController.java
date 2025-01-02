package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.model.Comment;
import cz.trkan.pproprojekt.model.Meme;
import cz.trkan.pproprojekt.model.Tag;
import cz.trkan.pproprojekt.repository.*;
import cz.trkan.pproprojekt.security.MyUserDetails;
import cz.trkan.pproprojekt.utils.FileUploadUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class MemeController {
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public MemeController(CategoryRepository categoryRepository, TagRepository tagRepository, MemeRepository memeRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.memeRepository = memeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/")
    protected String home(Model model) {
        model.addAttribute("memes", memeRepository.findAll());
        return "meme/list";
    }

    @GetMapping("/tag/{tagId}")
    protected String byTag(@PathVariable Long tagId, Model model) {
        var tag = tagRepository.findById(tagId).orElse(null);
        if (tag == null) {
            return "redirect:/";
        }
        model.addAttribute("memes", memeRepository.findMemesByTags(List.of(tag)));
        model.addAttribute("tag", tag);
        return "meme/list";
    }

    @GetMapping("/category/{categoryId}")
    protected String byCategory(@PathVariable Long categoryId, Model model) {
        var category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return "redirect:/";
        }
        model.addAttribute("memes", memeRepository.findMemesByCategory(category));
        model.addAttribute("category", category);
        return "meme/list";
    }

    @GetMapping("/403")
    protected String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/upload")
    protected String upload(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "meme/upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("category") Long categoryId, @RequestParam("tags") List<Long> tagIds, Model model, FileUploadUtil fileUploadUtil, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if (file.isEmpty()) {
            //model.addAttribute("errorMessage", "Please select a file to upload");
            bindingResult.rejectValue("file", "file", "Please select a file to upload");
            model.addAttribute("categories", categoryRepository.findAll());
            return "meme/upload";
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("video/"))) {
            bindingResult.rejectValue("file", "file", "Only image and video files are allowed");
            model.addAttribute("categories", categoryRepository.findAll());
            return "meme/upload";
        }

        try {
            // Save the file
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "uploads/images/";

            if(contentType.startsWith("video/")) {
                uploadDir = "uploads/videos/";
            }

            fileUploadUtil.saveFile(uploadDir, filename, file);

            // Create and save the meme
            Meme meme = new Meme();
            meme.setName(name);
            meme.setFilename(filename);
            meme.setPath(uploadDir + filename);
            meme.setCreated(new Date());
            meme.setCategory(categoryRepository.findById(categoryId).orElse(null));
            if (userDetails != null) {
                var user = userRepository.findByUsername(userDetails.getUsername());
                if (user != null) {
                    meme.setAuthor(user);
                }
            }

            List<Tag> tagList = tagRepository.findAllById(tagIds);
            meme.setTags(tagList);

            memeRepository.save(meme);

            return "redirect:/meme/" + meme.getId();
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Failed to upload file: " + e.getMessage());
            return "upload";
        }
    }

    @GetMapping("/meme/{id}")
    private String meme(@PathVariable Long id, Model model) {
        Meme meme = memeRepository.findById(id).orElse(null);
        if (meme == null) {
            return "redirect:/";
        }

        model.addAttribute("meme", meme);
        return "meme/detail";
    }

    @PostMapping("/meme/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String text,
                             @AuthenticationPrincipal MyUserDetails userDetails) {
        Meme meme = memeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meme Id:" + id));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setMeme(meme);
        if (userDetails != null) {
            var user = userRepository.findByUsername(userDetails.getUsername());
            if (user != null) {
                comment.setAuthor(user);
            }
        }
        comment.setCreated(new Date());

        commentRepository.save(comment);

        return "redirect:/meme/" + id;
    }
}
