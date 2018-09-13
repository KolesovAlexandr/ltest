package ru.edisoft.dto;

public class XmlRecordDTO {

    private String originalFileName;
    private String orderNumber;
    private String originalXml;
    private String transformedXml;

    public XmlRecordDTO(String originalFileName, String orderNumber, String originalXml, String transformedXml) {
        this.originalFileName = originalFileName;
        this.orderNumber = orderNumber;
        this.originalXml = originalXml;
        this.transformedXml = transformedXml;
    }

    public XmlRecordDTO() {
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOriginalXml() {
        return originalXml;
    }

    public void setOriginalXml(String originalXml) {
        this.originalXml = originalXml;
    }

    public String getTransformedXml() {
        return transformedXml;
    }

    public void setTransformedXml(String transformedXml) {
        this.transformedXml = transformedXml;
    }

}
