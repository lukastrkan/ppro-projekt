package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITagService {
    Tag findById(Long tagId);

    List<Tag> findAll();

    List<Tag> findAllById(List<Long> tagIds);

    Tag findByName(String name);

    void save(@Valid Tag tag);

    void delete(Tag tag);
}
