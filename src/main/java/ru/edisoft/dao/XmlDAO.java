package ru.edisoft.dao;

import ru.edisoft.entity.XmlRecord;

import java.util.List;

public interface XmlDAO {

    List<XmlRecord> getAll();

    XmlRecord getById(Integer id);

    XmlRecord getByOrderNumber(String order_number);

    void create(XmlRecord record);
}
