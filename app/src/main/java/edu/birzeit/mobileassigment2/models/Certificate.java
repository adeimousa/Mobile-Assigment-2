package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class Certificate {
    private int certificateId;
    private int teacherId;
    private int fieldId;
    private int certificateTypeId;
    private String organizationName;
    private String description;
    private Date fromDate;
    private Date toDate;

    public Certificate() {
    }

    public Certificate(int certificateId, int teacherId, int fieldId, int certificateTypeId, String organizationName, String description, Date fromDate, Date toDate) {
        this.certificateId = certificateId;
        this.teacherId = teacherId;
        this.fieldId = fieldId;
        this.certificateTypeId = certificateTypeId;
        this.organizationName = organizationName;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(int certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "certificateId=" + certificateId +
                ", teacherId=" + teacherId +
                ", fieldId=" + fieldId +
                ", certificateTypeId=" + certificateTypeId +
                ", organizationName='" + organizationName + '\'' +
                ", description='" + description + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
