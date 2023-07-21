package com.th3hero.projectmanagerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProjectManagerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagerServerApplication.class, args);
    }

}
