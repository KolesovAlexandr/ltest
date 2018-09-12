package ru.edisoft.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.edisoft.XmlRecord;
import ru.edisoft.XmlDAO;
import ru.edisoft.XmlRowMapper;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class XmlDAOImpl implements XmlDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<XmlRecord> getAll() {
        return jdbcTemplate.query("Select * from xmls", new XmlRowMapper());
    }

    @Override
    public XmlRecord getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("Select * from xmls where id = ?", new Object[]{id}, new XmlRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public XmlRecord getByOrderNumber(String order_number) {
        try {
            return jdbcTemplate.queryForObject("Select * from xmls where order_number = ?", new Object[]{order_number}, new XmlRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public Integer create(XmlRecord record) {
        return jdbcTemplate.update("insert into xmls (order_number, original_xml, transformed_xml, creation_date) " + "values(?,  ?, ?, ?)",
                new Object[]{record.getOrderNumber(), record.getOriginalXml(), record.getTransformedXml(), new Timestamp(System.currentTimeMillis())});
    }
}
