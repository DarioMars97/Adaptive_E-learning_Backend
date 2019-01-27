package com.adaptivelearning.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import static org.springframework.boot.SpringApplication.run;

@EntityScan(basePackageClasses = {ServerApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        run(ServerApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
