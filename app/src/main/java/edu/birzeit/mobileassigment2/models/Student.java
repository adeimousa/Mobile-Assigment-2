package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class Student {
    private int STUDENT_ID;
    private String FIRST_NAME;
    private String MIDDLE_NAME;
    private String LAST_NAME;
    private Date DOB;
    private String GENDER;
    private String PHONE;
    private String EMAIL;
    private int NATIONAL_ID;
    private int CLASS_ID;
    private String ADDRESS;

    public Student() {
    }

    public Student(int STUDENT_ID, String FIRST_NAME, String MIDDLE_NAME, String LAST_NAME, Date dateOfBirth, String GENDER, String PHONE, String EMAIL, int NATIONAL_ID, int CLASS_ID, String ADDRESS) {
        this.STUDENT_ID = STUDENT_ID;
        this.FIRST_NAME = FIRST_NAME;
        this.MIDDLE_NAME = MIDDLE_NAME;
        this.LAST_NAME = LAST_NAME;
        this.DOB = dateOfBirth;
        this.GENDER = GENDER;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
        this.NATIONAL_ID = NATIONAL_ID;
        this.CLASS_ID = CLASS_ID;
        this.ADDRESS = ADDRESS;
    }

    public int getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(int STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getMIDDLE_NAME() {
        return MIDDLE_NAME;
    }

    public void setMIDDLE_NAME(String MIDDLE_NAME) {
        this.MIDDLE_NAME = MIDDLE_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public Date getDateOfBirth() {
        return DOB;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.DOB = dateOfBirth;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
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

    public int getCLASS_ID() {
        return CLASS_ID;
    }

    public void setCLASS_ID(int CLASS_ID) {
        this.CLASS_ID = CLASS_ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + STUDENT_ID +
                ", firstName='" + FIRST_NAME + '\'' +
                ", middleName='" + MIDDLE_NAME + '\'' +
                ", lastName='" + LAST_NAME + '\'' +
                ", dateOfBirth=" + DOB +
                ", gender='" + GENDER + '\'' +
                ", phone='" + PHONE + '\'' +
                ", email='" + EMAIL + '\'' +
                ", nationalId=" + NATIONAL_ID +
                ", classId=" + CLASS_ID +
                ", address='" + ADDRESS + '\'' +
                '}';
    }
}
