package dev.mightyelephants.backend;

import dev.mightyelephants.backend.model.User;
import dev.mightyelephants.backend.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository repository) {
		return args -> {
			if(repository.count() == 0) {
				var user = new User("user", "password", "email", null);
				repository.save(user);
			}
		};
	}


}
