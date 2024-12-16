package cz.trkan.pproprojekt.repository;

import cz.trkan.pproprojekt.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeRepository extends JpaRepository<Meme, Long> {
    Meme findByName(String name);
}
