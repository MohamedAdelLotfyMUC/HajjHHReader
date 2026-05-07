package com.muc.rfid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RfidBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfidBackendApplication.class, args);
    }

}
