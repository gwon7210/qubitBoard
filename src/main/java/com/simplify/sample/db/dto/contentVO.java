package com.simplify.sample.db.dto;

 import java.sql.Timestamp;
import java.util.Date;

public class contentVO {

    private String title;
    private String content;
    private String user_id;
    private int delpass;
    private Timestamp date;
    private int id;

    public contentVO(String title, int delpass, String user_id, String content) {
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.delpass = delpass;
     }

    public contentVO(String title, int delpass, String content , int id) {
        this.title = title;
        this.content = content;
        this.delpass = delpass;
        this.id = id;
    }

    public contentVO(int id) {
        this.id = id;
    }

    public contentVO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getDelpass() {
        return delpass;
    }

    public void setDelpass(int delpass) {
        this.delpass = delpass;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
