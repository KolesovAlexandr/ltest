package ru.edisoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.edisoft.DbProcessor;
import ru.edisoft.XMLRecord;

import java.util.Collection;

@RestController
public class TestController {

    @Autowired
    private DbProcessor dbProcessor;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/find/{orderNumber}", method = RequestMethod.GET)
    public Collection<XMLRecord> findDocuments(@PathVariable(required = false) String orderNumber) {
        if (orderNumber != null && !orderNumber.isEmpty()) {
            return dbProcessor.getByOrderNumber(orderNumber);
        } else {
            return dbProcessor.getAll();
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public String select(@RequestParam(required = false) Integer id, @RequestParam(required = false) String orderNumber,
                         @RequestParam(required = false) Boolean original) {
        if (id == null && orderNumber == null) {
            return "Must be specified id or orderNumber";
        }
        if (id != null) {
            XMLRecord record = dbProcessor.getById(id);
            return original ? record.getOriginalXml() : record.getTransformedXml();
        }
        return null;
    }
}
