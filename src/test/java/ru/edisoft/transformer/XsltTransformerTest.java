package ru.edisoft.transformer;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.edisoft.Application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class XsltTransformerTest {
    @Autowired
    private XsltTransformer xsltTransformer;

    @Test
    public void transform() {
        try {
            URL originalFileUrl = Resources.getResource("testxmls/original_order.xml");
            String originalXml = FileUtils.readFileToString(new File(originalFileUrl.toURI()));
            URL outExampleFileUrl = Resources.getResource("testxmls/out_example.xml");
            String outExampleXml = FileUtils.readFileToString(new File(outExampleFileUrl.toURI()))
                    .replaceAll("\\r|\\n|\\s|\\t", "");
            String actual = xsltTransformer.transform(originalXml)
                    .replaceAll("\\r|\\n|\\s|\\t", "");
            assertEquals(outExampleXml, actual);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}