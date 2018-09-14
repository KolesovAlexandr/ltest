package ru.edisoft.processors;

import com.google.common.io.Resources;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static ru.edisoft.processors.impl.FileProcessorTest.ORIGINAL_FILE_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class XmlProcessorTest {
    @Autowired
    private XmlProcessor xmlProcessor;
    @Value("${zipPath}")
    private String zipPath;
    @Value("${pathOut}")
    private String pathOut;
    private XmlRecordDTO xmlRecordDTO;
    private String originalFilePath;
    private String transformedFilePath;
    private String zipFilePath;

    @Before
    public void setUp() {
        xmlRecordDTO = new XmlRecordDTO(ORIGINAL_FILE_NAME, XmlControllerTest.ORDER_NUMBER_1,
                XmlControllerTest.ORIGINAL_XML_1, XmlControllerTest.TRANSFORMED_XML_1);
        originalFilePath = pathOut + "/" + xmlRecordDTO.getOriginalFileName() + ".xml";
        transformedFilePath = pathOut + "/" + xmlRecordDTO.getOrderNumber() + ".xml";
        zipFilePath = zipPath + "/" + xmlRecordDTO.getOriginalFileName() + ".zip";
    }

    @Test
    public void process() {
        try {
            URI fileUri = Resources.getResource("testxmls/original_order.xml").toURI();
            xmlProcessor.process(new File(fileUri));
            assertTrue(Files.exists(Paths.get(originalFilePath)));
            assertTrue(Files.exists(Paths.get(transformedFilePath)));
            assertTrue(Files.exists(Paths.get(zipFilePath)));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOrderNumber() {
        try {
            URI fileUri = Resources.getResource("testxmls/out_example.xml").toURI();
            String outXml = FileUtils.readFileToString(new File(fileUri));
            String orderNumber = xmlProcessor.getOrderNumber(outXml);
            assertEquals("4513491158", orderNumber);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void clear() {
        try {
            Files.delete(Paths.get(originalFilePath));
            Files.delete(Paths.get(transformedFilePath));
            Files.delete(Paths.get(zipFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}