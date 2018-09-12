package ru.edisoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.edisoft.XmlRecord;
import ru.edisoft.XmlDAO;

import java.util.Collection;
import java.util.Collections;

@RestController
public class XmlController {

    @Autowired
    private XmlDAO dao;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = {"/find/{orderNumber}","/find"}, method = RequestMethod.GET)
    public Collection<XmlRecord> findDocuments(@PathVariable(required = false) String orderNumber) {
        if (orderNumber != null && !orderNumber.isEmpty()) {
            return Collections.singleton(dao.getByOrderNumber(orderNumber));
        } else {
            return dao.getAll();
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public String select(@RequestParam(required = false) Integer id, @RequestParam(required = false) String orderNumber,
                         @RequestParam(required = false) Boolean original) {
        XmlRecord record = null;
        if (id == null && orderNumber == null) {
            return "Must be specified id or orderNumber";
        }
        if (id != null) {
            record = dao.getById(id);

        }
        if (orderNumber != null) {
            record = dao.getByOrderNumber(orderNumber);
        }
        return record != null ? Boolean.TRUE.equals(original) ? record.getOriginalXml() : record.getTransformedXml() : "empty result";

    }
}
