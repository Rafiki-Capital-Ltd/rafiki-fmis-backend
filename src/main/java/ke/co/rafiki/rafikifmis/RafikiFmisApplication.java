package ke.co.rafiki.rafikifmis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RafikiFmisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RafikiFmisApplication.class, args);
	}

}
