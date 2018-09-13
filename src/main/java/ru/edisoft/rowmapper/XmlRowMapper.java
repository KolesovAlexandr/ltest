package ru.edisoft.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.edisoft.entity.XmlRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class XmlRowMapper implements RowMapper<XmlRecord> {

    @Override
    public XmlRecord mapRow(ResultSet rs, int i) throws SQLException {
        XmlRecord xmlRecord = new XmlRecord();
        xmlRecord.setId(rs.getLong("id"));
        xmlRecord.setOrderNumber(rs.getString("order_number"));
        xmlRecord.setOriginalXml(rs.getString("original_xml"));
        xmlRecord.setTransformedXml(rs.getString("transformed_xml"));
        xmlRecord.setCreationDate(rs.getDate("creation_date"));
        return xmlRecord;
    }
}
