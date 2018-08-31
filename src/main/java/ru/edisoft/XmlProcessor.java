package ru.edisoft;

import org.apache.camel.component.file.strategy.FileRenameExclusiveReadLockStrategy;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Component
public class XmlProcessor {

    @Autowired
    private XsltTransformer xsltTransformer;
    @Autowired
    private DbProcessor dbProcessor;

    @Value("${pathOut}")
    private String pathOut;


    public void process(File file) {
        try {
            ByteArrayOutputStream transformedXmlOS = xsltTransformer.transform(file);
            String transformedXmlSting = transformedXmlOS.toString();
            InputSource source = new InputSource(new StringReader(transformedXmlSting));
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath path = xpathfactory.newXPath();
            String orderNumber = path.evaluate("/Interchange/Group/Message/Document-Order/Order-Header/OrderNumber", source);
            String originalFilePath = pathOut + "/" + file.getName() + ".xml";
            String transformedFilePath = pathOut + "/" + orderNumber + ".xml";
            Integer id = dbProcessor.save(orderNumber, getString(file), transformedXmlSting);
            Files.write(Paths.get(originalFilePath), getByte(file));
            Files.write(Paths.get(transformedFilePath), transformedXmlOS.toByteArray());

        } catch (XPathExpressionException | IOException e) {
            e.printStackTrace();
        }
    }

    private String getString(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }

    private byte[] getByte(File file) {
        byte[] getBytes = {};
        try {
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytes;
    }

}
