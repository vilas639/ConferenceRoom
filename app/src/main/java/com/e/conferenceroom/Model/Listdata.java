package com.e.conferenceroom.Model;

public class Listdata {


    public String id;
    public String title;
    public String desc;
    public String selectroom;
    public String starttime;
    public String endtime;

    public Listdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }



    public Listdata(String id, String title, String desc, String selectroom, String starttime, String endtime) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.selectroom = selectroom;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSelectroom() {
        return selectroom;
    }

    public void setSelectroom(String selectroom) {
        this.selectroom = selectroom;
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
}