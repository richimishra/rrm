package com.project.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("com.project.project1.Entity")
//@ComponentScan("com.project.project1.Utils")
//@EnableJpaRepositories("com.project.project1.Repository")
//        (scanBasePackages={
//        "com.project.project1.Controller", "com.project.project1.Entity", "com.project.project1.Repository", "com.project.project1.Service", "com.project.project1.Utils", "com.project.project1.Application"})

public class Project1Application {

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }
}
