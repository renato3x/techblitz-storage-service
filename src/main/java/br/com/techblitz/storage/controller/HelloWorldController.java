package br.com.techblitz.storage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class HelloWorldController {
    @GetMapping
    public Map<String, String> helloWorld() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World!");
        return response;
    }
}
