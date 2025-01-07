package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Tag;
import cz.trkan.pproprojekt.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements ITagService{
    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findAllById(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }
}
