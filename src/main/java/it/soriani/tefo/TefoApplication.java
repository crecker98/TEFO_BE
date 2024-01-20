package it.soriani.tefo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TefoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TefoApplication.class, args);
    }

}
