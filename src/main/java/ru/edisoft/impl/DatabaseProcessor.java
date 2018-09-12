package ru.edisoft.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.edisoft.SaveProcessor;
import ru.edisoft.XmlRecord;
import ru.edisoft.XmlDAO;
import ru.edisoft.XmlRecordDTO;

@Repository
public class DatabaseProcessor implements SaveProcessor {

    @Autowired
    XmlDAO xmlDAO;

    @Override
    public void process(XmlRecordDTO dto) {
        xmlDAO.create(new XmlRecord(dto.getOrderNumber(), dto.getOriginalXml(), dto.getTransformedXml()));
    }
}
