package edu.birzeit.mobileassigment2.models;

public class TeacherClasses {
    private int TEACHER_CLASSES_ID ;
    private int TEACHER_ID ;
    private int CLASS_ID ;

    public TeacherClasses() {
    }

    public TeacherClasses(int TEACHER_CLASSES_ID, int TEACHER_ID, int CLASS_ID) {
        this.TEACHER_CLASSES_ID = TEACHER_CLASSES_ID;
        this.TEACHER_ID = TEACHER_ID;
        this.CLASS_ID = CLASS_ID;
    }

    public int getTEACHER_CLASSES_ID() {
        return TEACHER_CLASSES_ID;
    }

    public void setTEACHER_CLASSES_ID(int TEACHER_CLASSES_ID) {
        this.TEACHER_CLASSES_ID = TEACHER_CLASSES_ID;
    }

    public int getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(int TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public int getCLASS_ID() {
        return CLASS_ID;
    }

    public void setCLASS_ID(int CLASS_ID) {
        this.CLASS_ID = CLASS_ID;
    }

    @Override
    public String toString() {
        return "TeacherClasses{" +
                "TEACHER_CLASSES_ID=" + TEACHER_CLASSES_ID +
                ", TEACHER_ID=" + TEACHER_ID +
                ", CLASS_ID=" + CLASS_ID +
                '}';
    }
}
