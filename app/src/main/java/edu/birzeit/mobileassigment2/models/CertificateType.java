package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class CertificateType {

    private int CERTICATION_TYPE_ID;
    private String CERTIFICATION_TYPE_NAME;

    public CertificateType() {
    }

    public CertificateType(int CERTICATION_TYPE_ID, String CERTIFICATION_TYPE_NAME) {
        this.CERTICATION_TYPE_ID = CERTICATION_TYPE_ID;
        this.CERTIFICATION_TYPE_NAME = CERTIFICATION_TYPE_NAME;
    }

    @Override
    public String toString() {
        return "CertificateType{" +
                "CERTICATION_TYPE_ID=" + CERTICATION_TYPE_ID +
                ", CERTIFICATION_TYPE_NAME='" + CERTIFICATION_TYPE_NAME + '\'' +
                '}';
    }

    public int getCERTICATION_TYPE_ID() {
        return CERTICATION_TYPE_ID;
    }

    public void setCERTICATION_TYPE_ID(int CERTICATION_TYPE_ID) {
        this.CERTICATION_TYPE_ID = CERTICATION_TYPE_ID;
    }

    public String getCERTIFICATION_TYPE_NAME() {
        return CERTIFICATION_TYPE_NAME;
    }

    public void setCERTIFICATION_TYPE_NAME(String CERTIFICATION_TYPE_NAME) {
        this.CERTIFICATION_TYPE_NAME = CERTIFICATION_TYPE_NAME;
    }
}
