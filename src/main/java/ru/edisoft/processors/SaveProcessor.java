package ru.edisoft.processors;

import ru.edisoft.dto.XmlRecordDTO;

public interface SaveProcessor {
    void process(XmlRecordDTO xmlRecord);
}
