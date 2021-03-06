package ru.edisoft.processors.impl;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.edisoft.Application;
import ru.edisoft.controllers.XmlControllerTest;
import ru.edisoft.dto.XmlRecordDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class FileProcessorTest {
    public static final String ORIGINAL_FILE_NAME = "original_order";
    @Autowired
    private FileProcessor fileProcessor;

    @Value("${pathOut}")
    private String pathOut;
    private XmlRecordDTO xmlRecordDTO;
    private String originalFilePath;
    private String transformedFilePath;

    @Before
    public void setUp() {
        xmlRecordDTO = new XmlRecordDTO(ORIGINAL_FILE_NAME, XmlControllerTest.ORDER_NUMBER_1,
                XmlControllerTest.ORIGINAL_XML_1, XmlControllerTest.TRANSFORMED_XML_1);
        originalFilePath = pathOut + "/" + xmlRecordDTO.getOriginalFileName() + ".xml";
        transformedFilePath = pathOut + "/" + xmlRecordDTO.getOrderNumber() + ".xml";

    }

    @Test
    public void process() {
        fileProcessor.process(xmlRecordDTO);
        String originalXml = null;
        String transformedXml = null;
        try {
            originalXml = FileUtils.readFileToString(new File(originalFilePath));
            transformedXml = FileUtils.readFileToString(new File(transformedFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(xmlRecordDTO.getOriginalXml(), originalXml);
        assertEquals(xmlRecordDTO.getTransformedXml(), transformedXml);

    }

    @After
    public void clear() {
        try {
            Files.delete(Paths.get(originalFilePath));
            Files.delete(Paths.get(transformedFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}