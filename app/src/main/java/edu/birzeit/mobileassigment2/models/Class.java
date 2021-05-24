package edu.birzeit.mobileassigment2.models;

public class Class {
    private int CLASS_ID;
    private String NAME;
    private int SECTION_NUMBER;
    private int ROOM_NUMBER;
    private int CAPACITY;

    public Class() {
    }

    public Class(int CLASS_ID, String NAME, int SECTION_NUMBER, int ROOM_NUMBER, int CAPACITY) {
        this.CLASS_ID = CLASS_ID;
        this.NAME = NAME;
        this.SECTION_NUMBER = SECTION_NUMBER;
        this.ROOM_NUMBER = ROOM_NUMBER;
        this.CAPACITY = CAPACITY;
    }

    public int getCLASS_ID() {
        return CLASS_ID;
    }

    public void setCLASS_ID(int CLASS_ID) {
        this.CLASS_ID = CLASS_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getSECTION_NUMBER() {
        return SECTION_NUMBER;
    }

    public void setSECTION_NUMBER(int SECTION_NUMBER) {
        this.SECTION_NUMBER = SECTION_NUMBER;
    }

    public int getROOM_NUMBER() {
        return ROOM_NUMBER;
    }

    public void setROOM_NUMBER(int ROOM_NUMBER) {
        this.ROOM_NUMBER = ROOM_NUMBER;
    }

    public int getCAPACITY() {
        return CAPACITY;
    }

    public void setCAPACITY(int CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + CLASS_ID +
                ", name='" + NAME + '\'' +
                ", sectionNumber=" + SECTION_NUMBER +
                ", roomNumber=" + ROOM_NUMBER +
                ", capacity=" + CAPACITY +
                '}';
    }
}
