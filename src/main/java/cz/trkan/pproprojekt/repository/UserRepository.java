package cz.trkan.pproprojekt.repository;

import cz.trkan.pproprojekt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}