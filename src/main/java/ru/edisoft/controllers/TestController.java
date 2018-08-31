package ru.edisoft.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/find/{orderNumber}", method = RequestMethod.GET)
    public String findDocuments(@PathVariable(required = false) String orderNumber) {
        return "Greetings from Spring Boot!";
    }
    @RequestMapping(value = "/select", method= RequestMethod.POST)
    @ResponseBody
    public String select(@RequestParam(required = false) String id, @RequestParam(required = false) String orderNumber) {
        return "Greetings from Spring Boot!";
    }
}
