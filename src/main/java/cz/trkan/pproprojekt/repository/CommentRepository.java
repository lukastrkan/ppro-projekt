package cz.trkan.pproprojekt.repository;

import cz.trkan.pproprojekt.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
