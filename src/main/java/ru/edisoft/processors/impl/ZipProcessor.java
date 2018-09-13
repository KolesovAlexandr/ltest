package ru.edisoft.processors.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edisoft.processors.SaveProcessor;
import ru.edisoft.dto.XmlRecordDTO;

import java.io.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Repository
public class ZipProcessor implements SaveProcessor {
    private Logger logger = Logger.getLogger(ZipProcessor.class.getName());

    @Value("${zipPath}")
    private String zipPath;

    @Override
    public void process(XmlRecordDTO dto) {

        String zipFilePath = zipPath + "/" + dto.getOriginalFileName() + ".zip";
        try (OutputStream baos = new FileOutputStream(zipFilePath); ZipOutputStream zos = new ZipOutputStream(baos)) {

            ZipEntry originalFileEntry = new ZipEntry(dto.getOriginalFileName() + ".xml");
            zos.putNextEntry(originalFileEntry);
            zos.write(dto.getOriginalXml().getBytes());
            zos.closeEntry();

            ZipEntry transformedEntry = new ZipEntry(dto.getOrderNumber() + ".xml");
            zos.putNextEntry(transformedEntry);
            zos.write(dto.getTransformedXml().getBytes());
            zos.closeEntry();


        } catch (IOException ioe) {
            logger.warning("error during creating zip file");
        }


    }
}
