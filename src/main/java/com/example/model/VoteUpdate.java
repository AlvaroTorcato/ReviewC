package com.example.model;

public class VoteUpdate {
    private int id;
    private String status;

    public VoteUpdate(int id, boolean status) {
        this.id = id;
        setString(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setString(boolean status){
        if (status){
            this.status = "true";
        }
        else{
            this.status = "false";
        }
    }
}
