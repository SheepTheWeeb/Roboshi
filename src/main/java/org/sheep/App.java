package org.sheep;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@AllArgsConstructor
@EnableMongoRepositories(basePackages = "org.sheep.repository")
@EnableScheduling
@SpringBootApplication
public class App implements CommandLineRunner {
    private JDA jda;

    public static void main(String[] args) {
        log.info("Starting Roboshi");
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        jda.awaitReady();
    }
}
