package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Category;
import cz.trkan.pproprojekt.model.Meme;
import cz.trkan.pproprojekt.model.Tag;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public interface IMemeService {
    List<Meme> findAll();

    List<Meme> findMemesByTags(List<Tag> tag);

    List<Meme> findMemesByCategory(Category category);

    void save(Meme meme);

    Meme findById(Long id);

    void deleteById(Long id);
}
