package ru.edisoft.controllers;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UploadFile {


    void upload(String xmlPath,String xsltPath){
        try {
            File xslt = new File(xsltPath);
            StreamSource xsltSource = new StreamSource(xslt);
            TransformerFactory tf =
                    TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(xsltSource);

            String content = new String(Files.readAllBytes(Paths.get(xmlPath)));
            Files.delete(Paths.get(xmlPath));
            StreamSource xmlSource = new StreamSource(new StringBufferInputStream(content));
            StreamResult streamResult = new StreamResult();
            transformer.transform(xmlSource, streamResult);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
