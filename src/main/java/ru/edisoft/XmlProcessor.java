package ru.edisoft;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

import javax.jms.Message;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.List;

@Component("xmlProcessor")
public class XmlProcessor {

    @Autowired
    private XsltTransformer xsltTransformer;

    @Autowired
    List<SaveProcessor> saveProcessors;

    public void process(File file) {
        try {
            String fileName = file.getName();
            String[] separatedFileName = StringUtils.split(fileName, ".");
            if (separatedFileName[separatedFileName.length - 1].equals("xml")) {
                ByteArrayOutputStream transformedXmlOS = xsltTransformer.transform(file);
                String transformedXmlSting = transformedXmlOS.toString();
                String orderNumber = getOrderNumber(transformedXmlSting);
                XmlRecordDTO dto = new XmlRecordDTO(separatedFileName[0], orderNumber, getString(file), transformedXmlSting);
                for (SaveProcessor saveProcessor : saveProcessors) {
                    saveProcessor.process(dto);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private String getOrderNumber(String transformedXmlSting) throws XPathExpressionException {
        InputSource source = new InputSource(new StringReader(transformedXmlSting));
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath path = xpathfactory.newXPath();
        return path.evaluate("/Interchange/Group/Message/Document-Order/Order-Header/OrderNumber", source);
    }

    private String getString(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }


}
