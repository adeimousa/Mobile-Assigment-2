package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class Certificate {
    private int CERTIFICATE_ID ;
    private int TEACHER_ID ;
    private int FIELD_ID ;
    private int CERTIFICATE_TYPE_ID ;
    private String ORGANIZATION_NAME;
    private String DESCRIPTION;
    private Date FROM_DATE;
    private Date TO_DATE;

    public Certificate() {
    }

    public Certificate(int CERTIFICATE_ID, int TEACHER_ID, int FIELD_ID, int CERTIFICATE_TYPE_ID, String ORGANIZATION_NAME, String DESCRIPTION, Date FROM_DATE, Date TO_DATE) {
        this.CERTIFICATE_ID = CERTIFICATE_ID;
        this.TEACHER_ID = TEACHER_ID;
        this.FIELD_ID = FIELD_ID;
        this.CERTIFICATE_TYPE_ID = CERTIFICATE_TYPE_ID;
        this.ORGANIZATION_NAME = ORGANIZATION_NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.FROM_DATE = FROM_DATE;
        this.TO_DATE = TO_DATE;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "CERTIFICATE_ID=" + CERTIFICATE_ID +
                ", TEACHER_ID=" + TEACHER_ID +
                ", FIELD_ID=" + FIELD_ID +
                ", CERTIFICATE_TYPE_ID=" + CERTIFICATE_TYPE_ID +
                ", ORGANIZATION_NAME='" + ORGANIZATION_NAME + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", FROM_DATE=" + FROM_DATE +
                ", TO_DATE=" + TO_DATE +
                '}';
    }

    public int getCERTIFICATE_ID() {
        return CERTIFICATE_ID;
    }

    public void setCERTIFICATE_ID(int CERTIFICATE_ID) {
        this.CERTIFICATE_ID = CERTIFICATE_ID;
    }

    public int getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(int TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public int getFIELD_ID() {
        return FIELD_ID;
    }

    public void setFIELD_ID(int FIELD_ID) {
        this.FIELD_ID = FIELD_ID;
    }

    public int getCERTIFICATE_TYPE_ID() {
        return CERTIFICATE_TYPE_ID;
    }

    public void setCERTIFICATE_TYPE_ID(int CERTIFICATE_TYPE_ID) {
        this.CERTIFICATE_TYPE_ID = CERTIFICATE_TYPE_ID;
    }

    public String getORGANIZATION_NAME() {
        return ORGANIZATION_NAME;
    }

    public void setORGANIZATION_NAME(String ORGANIZATION_NAME) {
        this.ORGANIZATION_NAME = ORGANIZATION_NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public Date getFROM_DATE() {
        return FROM_DATE;
    }

    public void setFROM_DATE(Date FROM_DATE) {
        this.FROM_DATE = FROM_DATE;
    }

    public Date getTO_DATE() {
        return TO_DATE;
    }

    public void setTO_DATE(Date TO_DATE) {
        this.TO_DATE = TO_DATE;
    }
}
