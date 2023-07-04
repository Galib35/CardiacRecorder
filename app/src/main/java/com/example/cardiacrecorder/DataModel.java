package com.example.cardiacrecorder;

public class DataModel {
    String systolic,diastolic,heart_rate,date,time,comment;

    public DataModel() {
    }

    public DataModel(String systolic, String diastolic, String heart_rate, String date, String time, String comment) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heart_rate = heart_rate;
        this.date = date;
        this.time = time;
        this.comment = comment;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
