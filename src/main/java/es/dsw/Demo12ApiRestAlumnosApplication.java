package es.dsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"es.dsw"})
public class Demo12ApiRestAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo12ApiRestAlumnosApplication.class, args);
	}

}
