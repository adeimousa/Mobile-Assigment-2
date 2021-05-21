package edu.birzeit.mobileassigment2.models;

import java.util.Date;

public class Class {
    private int classId;
    private String name;
    private int sectionNumber;
    private int roomNumber;
    private int capacity;

    public Class() {
    }

    public Class(int classId, String name, int sectionNumber, int roomNumber, int capacity) {
        this.classId = classId;
        this.name = name;
        this.sectionNumber = sectionNumber;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", name='" + name + '\'' +
                ", sectionNumber=" + sectionNumber +
                ", roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                '}';
    }
}
