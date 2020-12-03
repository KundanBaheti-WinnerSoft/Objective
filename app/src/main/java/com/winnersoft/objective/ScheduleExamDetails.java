package com.winnersoft.objective;

public class ScheduleExamDetails {
    String id,dateofexam,coursename,batch,day,starttime,endtime,courseid,noofquestions,totalmarks;
    Boolean questionallocation=false,examcenterallocation=false;
//    Integer noofquestions,totalmarks;

    public ScheduleExamDetails(String id, String dateofexam, String coursename, String batch, String day, String starttime, String endtime, String courseid, Boolean questionallocation, Boolean examcenterallocation, String noofquestions, String totalmarks) {
        this.id = id;
        this.dateofexam = dateofexam;
        this.coursename = coursename;
        this.batch = batch;
        this.day = day;
        this.starttime = starttime;
        this.endtime = endtime;
        this.courseid = courseid;
        this.questionallocation = questionallocation;
        this.examcenterallocation = examcenterallocation;
        this.noofquestions = noofquestions;
        this.totalmarks = totalmarks;
    }

    public ScheduleExamDetails() {

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

    public Boolean getQuestionallocation() {
        return questionallocation;
    }

    public void setQuestionallocation(Boolean questionallocation) {
        this.questionallocation = questionallocation;
    }

    public Boolean getExamcenterallocation() {
        return examcenterallocation;
    }

    public void setExamcenterallocation(Boolean examcenterallocation) {
        this.examcenterallocation = examcenterallocation;
    }

    public String getNoofquestions() {
        return noofquestions;
    }

    public void setNoofquestions(String  noofquestions) {
        this.noofquestions = noofquestions;
    }

    public String getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(String totalmarks) {
        this.totalmarks = totalmarks;
    }
}
