package ru.edisoft.processors.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.edisoft.dao.XmlDAO;
import ru.edisoft.entity.XmlRecord;
import ru.edisoft.dto.XmlRecordDTO;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseProcessorTest {

    @Mock
    private XmlDAO dao;

    @InjectMocks
    private DatabaseProcessor databaseProcessor = new DatabaseProcessor();


    @Test
    public void process() {
        databaseProcessor.process(new XmlRecordDTO());
        verify(dao).create(any(XmlRecord.class));
    }
}