package ke.co.rafiki.fmis;

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
