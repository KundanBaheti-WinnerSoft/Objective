package com.winnersoft.objective;

public class QueSetGenDetails {
    String id,dateofexam,coursename,batch,day,starttime,endtime,courseid,noofquestions,totalmarks,seatnumber_allocation,seatnumber;
    Boolean question_allocation=false;


    public QueSetGenDetails(String id, String dateofexam, String coursename, String batch, String day, String starttime, String endtime, String courseid, String noofquestions, String totalmarks, String seatnumber_allocation, String seatnumber, Boolean question_allocation) {
        this.id = id;
        this.dateofexam = dateofexam;
        this.coursename = coursename;
        this.batch = batch;
        this.day = day;
        this.starttime = starttime;
        this.endtime = endtime;
        this.courseid = courseid;
        this.noofquestions = noofquestions;
        this.totalmarks = totalmarks;
        this.seatnumber_allocation = seatnumber_allocation;
        this.seatnumber = seatnumber;
        this.question_allocation = question_allocation;
    }

    public QueSetGenDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateofexam() {
        return dateofexam;
    }

    public void setDateofexam(String dateofexam) {
        this.dateofexam = dateofexam;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getNoofquestions() {
        return noofquestions;
    }

    public void setNoofquestions(String noofquestions) {
        this.noofquestions = noofquestions;
    }

    public String getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(String totalmarks) {
        this.totalmarks = totalmarks;
    }

    public String getSeatnumber_allocation() {
        return seatnumber_allocation;
    }

    public void setSeatnumber_allocation(String seatnumber_allocation) {
        this.seatnumber_allocation = seatnumber_allocation;
    }

    public String getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(String seatnumber) {
        this.seatnumber = seatnumber;
    }

    public Boolean getQuestion_allocation() {
        return question_allocation;
    }

    public void setQuestion_allocation(Boolean question_allocation) {
        this.question_allocation = question_allocation;
    }
}
