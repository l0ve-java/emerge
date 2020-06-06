package org.departure.emerge.app;

import org.departure.emerge.app.service.MarkService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Emerge {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Emerge.class, args);
        final MarkService bean = context.getBean(MarkService.class);
        System.out.println("Started");
    }
}
