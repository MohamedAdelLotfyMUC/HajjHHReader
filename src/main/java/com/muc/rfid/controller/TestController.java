package com.muc.rfid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }

    @PostMapping("/rfid")
    public String rfid(@RequestBody(required = false) String body) {
        System.out.println("BODY = " + body);
        return "OK";
    }
}
