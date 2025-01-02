package cz.trkan.pproprojekt.repository;

import cz.trkan.pproprojekt.model.Category;
import cz.trkan.pproprojekt.model.Meme;
import cz.trkan.pproprojekt.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemeRepository extends JpaRepository<Meme, Long> {
    Meme findByName(String name);
    List<Meme> findMemesByTags(List<Tag> tags);

    List<Meme> findMemesByCategory(Category category);
}
