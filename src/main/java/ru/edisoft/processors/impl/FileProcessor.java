package ru.edisoft.processors.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edisoft.processors.SaveProcessor;
import ru.edisoft.dto.XmlRecordDTO;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Repository
public class FileProcessor implements SaveProcessor {
    private Logger logger = Logger.getLogger(FileProcessor.class.getName());

    @Value("${pathOut}")
    private String pathOut;

    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(String pathOut) {
        this.pathOut = pathOut;
    }

    @Override
    public void process(XmlRecordDTO xmlRecord) {

        String originalFilePath = pathOut + "/" + xmlRecord.getOriginalFileName() + ".xml";
        String transformedFilePath = pathOut + "/" + xmlRecord.getOrderNumber() + ".xml";
        try {
            FileUtils.writeStringToFile(new File(originalFilePath), xmlRecord.getOriginalXml());
            FileUtils.writeStringToFile(new File(transformedFilePath), xmlRecord.getTransformedXml());
        } catch (IOException e) {
            logger.warning("error during creating files");
        }

    }
}
