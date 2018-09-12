package ru.edisoft.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edisoft.SaveProcessor;
import ru.edisoft.XmlRecordDTO;

import java.io.File;
import java.io.IOException;

@Repository
public class FileProcessor implements SaveProcessor {

    @Value("${pathOut}")
    private String pathOut;

    @Override
    public void process(XmlRecordDTO xmlRecord) {

        String originalFilePath = pathOut + "/" + xmlRecord.getOriginalFileName() + ".xml";
        String transformedFilePath = pathOut + "/" + xmlRecord.getOrderNumber() + ".xml";
        try {
            FileUtils.writeStringToFile(new File(originalFilePath), xmlRecord.getOriginalXml());
            FileUtils.writeStringToFile(new File(transformedFilePath), xmlRecord.getTransformedXml());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
