package ru.edisoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;

@Component("fileReaderProcessor")
public class FileReaderProcessor {

	@Autowired
	private XmlProcessor xmlProcessor;

	public void process(File body) {
		String fileName = body.getName();
		String[] separatedFileName = StringUtils.split(fileName, ".");
		if (separatedFileName[separatedFileName.length - 1].equals("xml")) {
			xmlProcessor.process(body);
		}
	}
}
