package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Comment;
import cz.trkan.pproprojekt.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService{
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
