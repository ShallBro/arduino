package org.example.arduinoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class ArduinoserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArduinoserverApplication.class, args);
    }

}
