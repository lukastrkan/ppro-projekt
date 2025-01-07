package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.model.Comment;
import cz.trkan.pproprojekt.model.Meme;
import cz.trkan.pproprojekt.model.Tag;
import cz.trkan.pproprojekt.security.MyUserDetails;
import cz.trkan.pproprojekt.service.*;
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

import static cz.trkan.pproprojekt.utils.JteUtils.isEditor;

@Controller
public class MemeController {
    private final ICategoryService categoryService;
    private final ITagService tagService;
    private final IMemeService memeService;
    private final IUserService userService;
    private final ICommentService commentService;
    private final FileUploadUtil fileUploadUtil;

    public MemeController(ICategoryService categoryService, ITagService tagService, IMemeService memeService, IUserService userService, ICommentService commentService, FileUploadUtil fileUploadUtil) {
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.memeService = memeService;
        this.userService = userService;
        this.commentService = commentService;
        this.fileUploadUtil = fileUploadUtil;
    }

    @GetMapping("/")
    protected String home(Model model) {
        model.addAttribute("memes", memeService.findAll().reversed());
        return "meme/list";
    }

    @GetMapping("/tag/{tagId}")
    protected String byTag(@PathVariable Long tagId, Model model) {
        var tag = tagService.findById(tagId);
        if (tag == null) {
            return "redirect:/";
        }
        model.addAttribute("memes", memeService.findMemesByTags(List.of(tag)));
        model.addAttribute("tag", tag);
        return "meme/list";
    }

    @GetMapping("/category/{categoryId}")
    protected String byCategory(@PathVariable Long categoryId, Model model) {
        var category = categoryService.findById(categoryId);
        if (category == null) {
            return "redirect:/";
        }
        model.addAttribute("memes", memeService.findMemesByCategory(category));
        model.addAttribute("category", category);
        return "meme/list";
    }

    @GetMapping("/403")
    protected String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/upload")
    protected String upload(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("tags", tagService.findAll());
        return "meme/upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("category") Long categoryId, @RequestParam(value = "tags", required = false) List<Long> tagIds, Model model, FileUploadUtil fileUploadUtil, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if (file.isEmpty()) {
            //model.addAttribute("errorMessage", "Please select a file to upload");
            bindingResult.rejectValue("file", "file", "Please select a file to upload");
            model.addAttribute("categories", categoryService.findAll());
            return "meme/upload";
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("video/"))) {
            bindingResult.rejectValue("file", "file", "Only image and video files are allowed");
            model.addAttribute("categories", categoryService.findAll());
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
            meme.setCategory(categoryService.findById(categoryId));
            if (userDetails != null) {
                var user = userService.findByUsername(userDetails.getUsername());
                if (user != null) {
                    meme.setAuthor(user);
                }
            }

            if (tagIds != null) {
                List<Tag> tagList = tagService.findAllById(tagIds);
                meme.setTags(tagList);
            }

            memeService.save(meme);

            return "redirect:/meme/" + meme.getId();
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Failed to upload file: " + e.getMessage());
            return "upload";
        }
    }

    @GetMapping("/meme/{id}")
    private String meme(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        Meme meme = memeService.findById(id);
        if (meme == null) {
            return "redirect:/";
        }

        if (userDetails != null && (userDetails.getUsername().equals(meme.getAuthor().getUsername()) || isEditor())) {
            model.addAttribute("canEdit", true);
        }

        model.addAttribute("meme", meme);
        return "meme/detail";
    }

    @PostMapping("/meme/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String text,
                             @AuthenticationPrincipal MyUserDetails userDetails) {
        Meme meme = memeService.findById(id);
        if (meme == null) {
            return "redirect:/";
        }

        Comment comment = new Comment();
        comment.setText(text);
        comment.setMeme(meme);
        if (userDetails != null) {
            var user = userService.findByUsername(userDetails.getUsername());
            if (user != null) {
                comment.setAuthor(user);
            }
        }
        comment.setCreated(new Date());

        commentService.save(comment);

        return "redirect:/meme/" + id;
    }

    @GetMapping("/meme/{id}/edit")
    private String editMeme(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        Meme meme = memeService.findById(id);
        if (meme == null) {
            return "redirect:/";
        }

        if (userDetails == null || (!meme.getAuthor().getUsername().equals(userDetails.getUsername()) && !isEditor())) {
            return "redirect:/meme/" + id;
        }

        model.addAttribute("meme", meme);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("tags", tagService.findAll());
        return "meme/edit";
    }

    @PostMapping("/meme/{id}/edit")
    private String editMeme(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("category") Long categoryId, @RequestParam("tags") List<Long> tagIds, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        Meme meme = memeService.findById(id);
        if (meme == null) {
            return "redirect:/";
        }

        if (userDetails == null || (!meme.getAuthor().getUsername().equals(userDetails.getUsername()) && !isEditor())) {
            return "redirect:/meme/" + id;
        }

        meme.setName(name);
        meme.setCategory(categoryService.findById(categoryId));
        List<Tag> tagList = tagService.findAllById(tagIds);
        meme.setTags(tagList);

        memeService.save(meme);

        return "redirect:/meme/" + id;
    }

    @GetMapping("/meme/{id}/delete")
    private String deleteMeme(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails userDetails) {
        Meme meme = memeService.findById(id);
        if (meme == null) {
            return "redirect:/";
        }

        if (userDetails == null || (!meme.getAuthor().getUsername().equals(userDetails.getUsername()) && !isEditor())) {
            return "redirect:/meme/" + id;
        }

        var path = meme.getPath();
        meme = null;
        memeService.deleteById(id);
        fileUploadUtil.deleteFile(path);

        return "redirect:/";
    }
}
