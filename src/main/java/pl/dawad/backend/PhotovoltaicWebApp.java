package pl.dawad.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PhotovoltaicWebApp {

	public static void main(String[] args) {
		SpringApplication.run(PhotovoltaicWebApp.class, args);
	}

}
