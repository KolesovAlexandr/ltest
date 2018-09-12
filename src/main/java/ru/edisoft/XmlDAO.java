package ru.edisoft;

import java.util.List;

public interface XmlDAO {

    List<XmlRecord> getAll();

    XmlRecord getById(Integer id);

    XmlRecord getByOrderNumber(String order_number);

    Integer create(XmlRecord record);
}
