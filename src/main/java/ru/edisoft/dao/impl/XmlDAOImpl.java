package ru.edisoft.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.edisoft.entity.XmlRecord;
import ru.edisoft.dao.XmlDAO;
import ru.edisoft.rowmapper.XmlRowMapper;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class XmlDAOImpl implements XmlDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public void create(XmlRecord record) {
        jdbcTemplate.update("insert into xmls (order_number, original_xml, transformed_xml, creation_date) " + "values(?,  ?, ?, ?)",
                new Object[]{record.getOrderNumber(), record.getOriginalXml(), record.getTransformedXml(), new Timestamp(System.currentTimeMillis())});
    }
}
