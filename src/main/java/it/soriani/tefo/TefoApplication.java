package it.soriani.tefo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.soriani.tefo.repository")
public class TefoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TefoApplication.class, args);
    }

}
