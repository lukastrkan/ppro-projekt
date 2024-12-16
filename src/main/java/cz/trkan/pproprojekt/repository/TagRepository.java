package cz.trkan.pproprojekt.repository;

import cz.trkan.pproprojekt.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
