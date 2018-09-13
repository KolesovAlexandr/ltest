package ru.edisoft.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.edisoft.controllers.XmlControllerTest;
import ru.edisoft.entity.XmlRecord;
import ru.edisoft.rowmapper.XmlRowMapper;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class XmlDAOImplTest {

    public static final int ID = 123;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private XmlDAOImpl xmlDAO;

    @Test
    public void getAll() {
        when(jdbcTemplate.query(eq("Select * from xmls"), any(XmlRowMapper.class)))
                .thenReturn(XmlControllerTest.XML_RECORDS_LIST);
        List<XmlRecord> actual = xmlDAO.getAll();
        assertArrayEquals(XmlControllerTest.XML_RECORDS_LIST.toArray(), actual.toArray());
    }

    @Test
    public void getByIdWithResult() {
        when(jdbcTemplate.queryForObject(eq("Select * from xmls where id = ?"), eq(new Object[]{XmlControllerTest.RECORD_ID}), any(XmlRowMapper.class)))
                .thenReturn(XmlControllerTest.XML_RECORD_1);
        XmlRecord actual = xmlDAO.getById(XmlControllerTest.RECORD_ID);
        assertEquals(XmlControllerTest.XML_RECORD_1, actual);
    }

    @Test
    public void getByIdWithoutResult() {
        when(jdbcTemplate.queryForObject(eq("Select * from xmls where id = ?"), eq(new Object[]{ID}), any(XmlRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));
        XmlRecord actual = xmlDAO.getById(ID);
        assertNull(actual);
    }

    @Test
    public void getByOrderNumberWithResult() {
        when(jdbcTemplate.queryForObject(eq("Select * from xmls where order_number = ?"), eq(new Object[]{XmlControllerTest.ORDER_NUMBER_1}), any(XmlRowMapper.class)))
                .thenReturn(XmlControllerTest.XML_RECORD_1);
        XmlRecord actual = xmlDAO.getByOrderNumber(XmlControllerTest.ORDER_NUMBER_1);
        assertEquals(XmlControllerTest.XML_RECORD_1, actual);
    }

    @Test
    public void getByOrderNumberWithoutResult() {
        when(jdbcTemplate.queryForObject(eq("Select * from xmls where order_number = ?"), eq(new Object[]{"OrdNmbr"}), any(XmlRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));
        XmlRecord actual = xmlDAO.getByOrderNumber("OrdNmbr");
        assertNull(actual);
    }

    @Test
    public void create() {
        XmlRecord record = XmlControllerTest.XML_RECORD_1;
        xmlDAO.create(record);
        verify(jdbcTemplate).update(eq("insert into xmls (order_number, original_xml, transformed_xml, creation_date) " + "values(?,  ?, ?, ?)"),
                eq(record.getOrderNumber()), eq(record.getOriginalXml()), eq(record.getTransformedXml()), any(Timestamp.class));
    }
}