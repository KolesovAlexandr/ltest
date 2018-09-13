package ru.edisoft.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Objects;

@XmlRootElement
public class XmlRecord {
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XmlRecord xmlRecord = (XmlRecord) o;
        return Objects.equals(id, xmlRecord.id) &&
                Objects.equals(orderNumber, xmlRecord.orderNumber) &&
                Objects.equals(originalXml, xmlRecord.originalXml) &&
                Objects.equals(transformedXml, xmlRecord.transformedXml) &&
                Objects.equals(creationDate, xmlRecord.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, originalXml, transformedXml, creationDate);
    }

    private String orderNumber;
    private String originalXml;
    private String transformedXml;
    private Date creationDate;

    public XmlRecord() {
    }

    public XmlRecord(String orderNumber, String originalXml, String transformedXml) {
        this.orderNumber = orderNumber;
        this.originalXml = originalXml;
        this.transformedXml = transformedXml;
    }


    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @XmlElement
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOriginalXml(String originalXml) {
        this.originalXml = originalXml;
    }

    @XmlElement
    public String getOriginalXml() {
        return originalXml;
    }

    public void setTransformedXml(String transformedXml) {
        this.transformedXml = transformedXml;
    }

    @XmlElement
    public String getTransformedXml() {
        return transformedXml;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement
    public Date getCreationDate() {
        return creationDate;
    }

}
