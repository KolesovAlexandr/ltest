package ru.edisoft.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.edisoft.dao.XmlDAO;
import ru.edisoft.entity.XmlRecord;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class XmlControllerTest {
    public static final String ORDER_NUMBER_1 = "4513491158";
    public static final String ORDER_NUMBER_2 = "order2";
    public static final String ORDER_NUMBER_3 = "order3";
    public static final String ORIGINAL_XML_1 = "originalXml1";
    public static final String ORIGINAL_XML_2 = "originalXml2";
    public static final String ORIGINAL_XML_3 = "originalXml3";
    public static final String TRANSFORMED_XML_1 = "transformedXml1";
    public static final String TRANSFORMED_XML_2 = "transformedXml2";
    public static final String TRANSFORMED_XML_3 = "transformedXml3";
    public static final XmlRecord XML_RECORD_1 = new XmlRecord(ORDER_NUMBER_1, ORIGINAL_XML_1, TRANSFORMED_XML_1);
    public static final XmlRecord XML_RECORD_2 = new XmlRecord(ORDER_NUMBER_2, ORIGINAL_XML_2, TRANSFORMED_XML_2);
    public static final XmlRecord XML_RECORD_3 = new XmlRecord(ORDER_NUMBER_3, ORIGINAL_XML_3, TRANSFORMED_XML_3);
    public static final List<XmlRecord> XML_RECORDS_LIST = Arrays.asList(XML_RECORD_1, XML_RECORD_2, XML_RECORD_3);
    public static final int RECORD_ID = 1;
    @Mock
    private XmlDAO dao;

    @InjectMocks
    private XmlController xmlController;


    @Test
    public void findDocumentsWithOutOrderNumber() {
        xmlController.findDocuments(null);
        verify(dao).getAll();
        List<XmlRecord> expected = XML_RECORDS_LIST;
        when(dao.getAll()).thenReturn(expected);
        Collection<XmlRecord> actual = xmlController.findDocuments(null);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void findDocumentsByOrderNumber() {
        xmlController.findDocuments(ORDER_NUMBER_1);
        verify(dao).getByOrderNumber(eq(ORDER_NUMBER_1));
        when(dao.getByOrderNumber(eq(ORDER_NUMBER_1))).thenReturn(XML_RECORD_1);
        Collection<XmlRecord> expected = Arrays.asList(XML_RECORD_1);
        Collection<XmlRecord> actual = xmlController.findDocuments(ORDER_NUMBER_1);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void selectNullableAllArguments() {
        String actual = xmlController.select(null, null, null);
        assertEquals(XmlController.ERROR_MESSAGE, actual);
    }

    @Test
    public void selectByIdTransformXml() {
        when(dao.getById(eq(RECORD_ID))).thenReturn(XML_RECORD_1);
        String actual = xmlController.select(RECORD_ID, null, null);
        verify(dao).getById(eq(RECORD_ID));
        assertEquals(TRANSFORMED_XML_1, actual);
    }

    @Test
    public void selectByIdOriginalXml() {
        when(dao.getById(eq(RECORD_ID))).thenReturn(XML_RECORD_1);
        String actual = xmlController.select(RECORD_ID, null, true);
        verify(dao).getById(eq(RECORD_ID));
        assertEquals(ORIGINAL_XML_1, actual);
    }

    @Test
    public void selectByOrderNumberTransformXml() {
        when(dao.getByOrderNumber(eq(ORDER_NUMBER_1))).thenReturn(XML_RECORD_1);
        String actual = xmlController.select(null, ORDER_NUMBER_1, null);
        verify(dao).getByOrderNumber(eq(ORDER_NUMBER_1));
        assertEquals(TRANSFORMED_XML_1, actual);
    }

    @Test
    public void selectByOrderNumberOriginalXml() {
        when(dao.getByOrderNumber(eq(ORDER_NUMBER_1))).thenReturn(XML_RECORD_1);
        String actual = xmlController.select(null, ORDER_NUMBER_1, true);
        verify(dao).getByOrderNumber(eq(ORDER_NUMBER_1));
        assertEquals(ORIGINAL_XML_1, actual);
    }
}