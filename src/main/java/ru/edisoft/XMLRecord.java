package ru.edisoft;

import java.sql.Date;

public class XMLRecord {
    private Long id;
    private String orderNumber;
    private String originalXml;
    private String transformedXml;
    private Date creationDate;

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOriginalXml(String originalXml) {
        this.originalXml = originalXml;
    }

    public String getOriginalXml() {
        return originalXml;
    }

    public void setTransformedXml(String transformedXml) {
        this.transformedXml = transformedXml;
    }

    public String getTransformedXml() {
        return transformedXml;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
