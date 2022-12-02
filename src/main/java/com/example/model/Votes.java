package com.example.model;

public class Votes {
    private int id;
    private int upVotes;
    private int downVotes;
    private int totalVotes;

    public Votes(int id, int upVotes, int downVotes, int totalVotes) {
        this.id = id;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.totalVotes = totalVotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
