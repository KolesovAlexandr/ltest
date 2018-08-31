package ru.edisoft;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Component
public class XsltTransformer {
    @Value("${xsltFile}")
    private String xstlFile;

    public ByteArrayOutputStream transform(File file) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(xstlFile));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Transformer transformer = factory.newTransformer(xslt);
            Source text = new StreamSource(file);
            transformer.transform(text, new StreamResult(outputStream));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
