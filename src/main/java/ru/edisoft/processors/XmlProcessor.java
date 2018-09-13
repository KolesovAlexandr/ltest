package ru.edisoft.processors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;
import ru.edisoft.dto.XmlRecordDTO;
import ru.edisoft.transformer.XsltTransformer;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

@Component("xmlProcessor")
public class XmlProcessor {

    private static final Logger LOGGER = Logger.getLogger(XmlProcessor.class.getName());


    @Autowired
    private XsltTransformer xsltTransformer;

    @Autowired
    List<SaveProcessor> saveProcessors;
    @Value("${xpath.expression}")
    private String expression;

    public void process(File file) {
        String fileName = file.getName();
        String[] separatedFileName = StringUtils.split(fileName, ".");
        if (separatedFileName[separatedFileName.length - 1].equals("xml")) {
            String originalXml = getString(file);
            String transformedXml = xsltTransformer.transform(originalXml);
            String orderNumber = getOrderNumber(transformedXml);
            XmlRecordDTO dto = new XmlRecordDTO(separatedFileName[0], orderNumber, originalXml, transformedXml);
            for (SaveProcessor saveProcessor : saveProcessors) {
                saveProcessor.process(dto);
            }
        }
    }

    private String getOrderNumber(String transformedXmlSting) {
        InputSource source = new InputSource(new StringReader(transformedXmlSting));
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath path = xpathfactory.newXPath();
        try {
            return path.evaluate(expression, source);
        } catch (XPathExpressionException e) {
            LOGGER.warning("xpath didn't evaluate");
            return null;
        }
    }

    private String getString(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            LOGGER.warning("file didn't read");
            return null;
        }
    }


}
