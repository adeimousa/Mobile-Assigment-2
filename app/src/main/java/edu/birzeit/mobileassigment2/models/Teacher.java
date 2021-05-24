package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class Teacher {
    private int TEACHER_ID ;
    private String FULL_NAME;
    private Date DOB;
    private String PHONE;
    private String EMAIL;
    private int NATIONAL_ID;
    private int FIELD_ID ;
    private String ADDRESS;

    public Teacher() {
    }

    public Teacher(int TEACHER_ID, String FULL_NAME, Date DOB, String PHONE, String EMAIL, int NATIONAL_ID, int FIELD_ID, String ADDRESS) {
        this.TEACHER_ID = TEACHER_ID;
        this.FULL_NAME = FULL_NAME;
        this.DOB = DOB;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
        this.NATIONAL_ID = NATIONAL_ID;
        this.FIELD_ID = FIELD_ID;
        this.ADDRESS = ADDRESS;
    }

    public int getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(int TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public int getNATIONAL_ID() {
        return NATIONAL_ID;
    }

    public void setNATIONAL_ID(int NATIONAL_ID) {
        this.NATIONAL_ID = NATIONAL_ID;
    }

    public int getFIELD_ID() {
        return FIELD_ID;
    }

    public void setFIELD_ID(int FIELD_ID) {
        this.FIELD_ID = FIELD_ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "TEACHER_ID=" + TEACHER_ID +
                ", FULL_NAME='" + FULL_NAME + '\'' +
                ", DOB=" + DOB +
                ", PHONE='" + PHONE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", NATIONAL_ID=" + NATIONAL_ID +
                ", FIELD_ID=" + FIELD_ID +
                ", ADDRESS='" + ADDRESS + '\'' +
                '}';
    }
}
