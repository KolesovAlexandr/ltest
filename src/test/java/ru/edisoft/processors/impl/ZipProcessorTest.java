package ru.edisoft.processors.impl;

import org.apache.commons.io.IOUtils;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.*;
import static ru.edisoft.processors.impl.FileProcessorTest.ORIGINAL_FILE_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ZipProcessorTest {
    @Autowired
    private ZipProcessor zipProcessor;

    @Value("${zipPath}")
    private String zipPath;
    private XmlRecordDTO xmlRecordDTO;
    private String zipFilePath;

    @Before
    public void setUp() {
        xmlRecordDTO = new XmlRecordDTO(ORIGINAL_FILE_NAME, XmlControllerTest.ORDER_NUMBER_1,
                XmlControllerTest.ORIGINAL_XML_1, XmlControllerTest.TRANSFORMED_XML_1);
        zipFilePath = zipPath + "/" + xmlRecordDTO.getOriginalFileName() + ".zip";

    }

    @Test
    public void process() {
        zipProcessor.process(xmlRecordDTO);
        String originalXml = null;
        String transformedXml = null;
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {

            ZipEntry originalfileEntry = zipFile.getEntry(xmlRecordDTO.getOriginalFileName() + ".xml");
            ZipEntry transformedXmlEntry = zipFile.getEntry(xmlRecordDTO.getOrderNumber() + ".xml");
            originalXml = IOUtils.toString(zipFile.getInputStream(originalfileEntry));
            transformedXml = IOUtils.toString(zipFile.getInputStream(transformedXmlEntry));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(xmlRecordDTO.getOriginalXml(), originalXml);
        assertEquals(xmlRecordDTO.getTransformedXml(), transformedXml);


    }

    @After
    public void clear() {
        try {
            Files.delete(Paths.get(zipFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}