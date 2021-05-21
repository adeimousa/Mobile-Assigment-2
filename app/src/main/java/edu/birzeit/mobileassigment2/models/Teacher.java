package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class Teacher {
    private int teacherId;
    private String fullName;
    private Date dateOfBirth;
    private String phone;
    private String email;
    private int nationalId;
    private int fieldId;
    private String address;

    public Teacher() {
    }

    public Teacher(int teacherId, String fullName, Date dateOfBirth, String phone, String email, int nationalId, int fieldId, String address) {
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.nationalId = nationalId;
        this.fieldId = fieldId;
        this.address = address;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", nationalId=" + nationalId +
                ", fieldId=" + fieldId +
                ", address='" + address + '\'' +
                '}';
    }
}
