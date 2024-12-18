package cz.trkan.pproprojekt.service;

import cz.trkan.pproprojekt.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends UserDetailsService {
    User findByUsername(String username);
    User save(User user);
}
