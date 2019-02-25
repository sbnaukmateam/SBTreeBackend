package org.sbteam.sbtree.db.pojo;

import java.io.Serializable;

public class KMADegree implements Serializable {

    private int year;

    private String faculty;

    private String speciality;

    private String program;
    
    public KMADegree() {}

    public KMADegree(int year, String faculty, String speciality) {
        this.year = year;
        this.faculty = faculty;
        this.speciality = speciality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}