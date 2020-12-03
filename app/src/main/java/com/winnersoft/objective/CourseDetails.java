package com.winnersoft.objective;

public class CourseDetails {
//    Double coursefees;
    String coursecode,coursename,coursedesc,courseduration,coursefees,id;


    public CourseDetails(String coursecode, String coursename, String coursedesc, String courseduration, String coursefees, String id) {
        this.coursecode = coursecode;
        this.coursename = coursename;
        this.coursedesc = coursedesc;
        this.courseduration = courseduration;
        this.coursefees = coursefees;
        this.id = id;
    }

    public CourseDetails() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursefees() {
        return coursefees;
    }

    public void setCoursefees(String coursefees) {
        this.coursefees = coursefees;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoursedesc() {
        return coursedesc;
    }

    public void setCoursedesc(String coursedesc) {
        this.coursedesc = coursedesc;
    }

    public String getCourseduration() {
        return courseduration;
    }

    public void setCourseduration(String courseduration) {
        this.courseduration = courseduration;
    }
}
