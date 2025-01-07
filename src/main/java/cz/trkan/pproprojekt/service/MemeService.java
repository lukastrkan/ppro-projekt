package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.Category;
import cz.trkan.pproprojekt.model.Meme;
import cz.trkan.pproprojekt.model.Tag;
import cz.trkan.pproprojekt.repository.MemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemeService implements IMemeService{
    private MemeRepository memeRepository;

    public MemeService(MemeRepository memeRepository){
        this.memeRepository = memeRepository;
    }

    @Override
    public List<Meme> findAll() {
        return memeRepository.findAll();
    }

    @Override
    public List<Meme> findMemesByTags(List<Tag> tag) {
        return memeRepository.findMemesByTags(tag);
    }

    @Override
    public List<Meme> findMemesByCategory(Category category) {
        return memeRepository.findMemesByCategory(category);
    }

    @Override
    public void save(Meme meme) {
        memeRepository.save(meme);
    }

    @Override
    public Meme findById(Long id) {
        return memeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        memeRepository.deleteById(id);
    }
}
