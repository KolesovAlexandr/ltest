package ru.edisoft.transformer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class XsltTransformer {
    @Value("${xsltFile}")
    private String xstlFile;

    public String transform(String xml) {
        StringReader reader = new StringReader(xml);
        StringWriter writer = new StringWriter();
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(xstlFile));
        try {
            Transformer transformer = factory.newTransformer(xslt);
            transformer.transform(new StreamSource(reader), new StreamResult(writer));
        } catch (TransformerException e) {
            return null;
        }
        return writer.toString();

    }
}
