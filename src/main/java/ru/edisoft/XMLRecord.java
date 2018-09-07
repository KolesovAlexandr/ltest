package ru.edisoft;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement
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

    public static XMLRecord generateError(String errorMsg) {
        XMLRecord error = new XMLRecord();
        error.setOriginalXml(errorMsg);
        error.setTransformedXml(errorMsg);
        return error;
    }
}
