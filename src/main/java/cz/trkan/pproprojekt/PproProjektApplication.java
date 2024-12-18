package cz.trkan.pproprojekt;

import cz.trkan.pproprojekt.model.User;
import cz.trkan.pproprojekt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PproProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(PproProjektApplication.class, args);
    }

    private IUserService IUserService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PproProjektApplication(IUserService IUserService, PasswordEncoder passwordEncoder) {
        this.IUserService = IUserService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            addUser("admin@memes.local", "admin", "heslo", "ADMIN");
            addUser("user@memes.local", "user", "heslo", "USER");
            addUser("user2@memes.local", "user2", "heslo", "USER");
        };
    }

    private void addUser(String email, String username, String password, String role) {
        if (IUserService.findByUsername(username) == null) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            IUserService.save(user);
        }
    }
}
