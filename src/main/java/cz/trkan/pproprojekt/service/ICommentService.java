package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Comment;
import org.springframework.stereotype.Service;

@Service
public interface ICommentService {
    void save(Comment comment);
}
