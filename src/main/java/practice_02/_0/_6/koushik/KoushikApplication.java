package practice_02._0._6.koushik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KoushikApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoushikApplication.class, args);
	}
 
}
