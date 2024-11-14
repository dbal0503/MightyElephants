package dev.mightyelephants.backend;

import dev.mightyelephants.backend.model.ShippingLabel;
import dev.mightyelephants.backend.model.User;
import dev.mightyelephants.backend.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

		ShippingLabel shippingLabel = new ShippingLabel();
		System.out.println(shippingLabel.getTrackingNumber());

		ShippingLabel shippingLabel2 = new ShippingLabel();
		System.out.println(shippingLabel2.getTrackingNumber());
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
