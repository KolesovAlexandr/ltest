package ru.edisoft;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DbProcessor {
    JdbcTemplate jdbcTemplate;

    public Integer save(String orderNumber, String originalXml, String transformedXml) {

        return jdbcTemplate.update("insert into xmls (order_number, original_xml, transformed_xml, creation_date) " + "values(?,  ?, ?)",
                new Object[]{orderNumber, originalXml, transformedXml, System.currentTimeMillis()});
    }

    class MyRowMapper implements RowMapper<XMLRecord> {

        @Override
        public XMLRecord mapRow(ResultSet rs, int i) throws SQLException {
            XMLRecord xmlRecord = new XMLRecord();
            xmlRecord.setId(rs.getLong("id"));
            xmlRecord.setOrderNumber(rs.getString("order_number"));
            xmlRecord.setOriginalXml(rs.getString("original_xml"));
            xmlRecord.setTransformedXml(rs.getString("transformed_xml"));
            xmlRecord.setCreationDate(rs.getDate("creation_date"));
            return xmlRecord;
        }
    }

    public List<XMLRecord> getAll() {
        return jdbcTemplate.query("Select * from xmls", new MyRowMapper());
    }

    public XMLRecord getById(Integer id) {
        return jdbcTemplate.queryForObject("Select * from xmls where id = ?", new Object[]{id}, new MyRowMapper());
    }

    public XMLRecord getByOrderNumber(String order_number) {
        return jdbcTemplate.queryForObject("Select * from xmls where order_number = ?", new Object[]{order_number}, new MyRowMapper());
    }

}
