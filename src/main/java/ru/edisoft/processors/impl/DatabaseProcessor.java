package ru.edisoft.processors.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.edisoft.processors.SaveProcessor;
import ru.edisoft.entity.XmlRecord;
import ru.edisoft.dao.XmlDAO;
import ru.edisoft.dto.XmlRecordDTO;

@Repository
public class DatabaseProcessor implements SaveProcessor {

    @Autowired
    private XmlDAO xmlDAO;

    @Override
    public void process(XmlRecordDTO dto) {
        xmlDAO.create(new XmlRecord(dto.getOrderNumber(), dto.getOriginalXml(), dto.getTransformedXml()));
    }
}
