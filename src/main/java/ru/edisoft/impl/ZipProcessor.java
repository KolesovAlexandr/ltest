package ru.edisoft.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edisoft.SaveProcessor;
import ru.edisoft.XmlRecordDTO;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Repository
public class ZipProcessor implements SaveProcessor {

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
            ioe.printStackTrace();
        }


    }
}
