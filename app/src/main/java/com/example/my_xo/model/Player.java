package com.example.my_xo.model;

public class Player {
    int id;
    String name;
    int score;

    public Player(int id,String name,int score){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Player(String name,int score){
        this.name = name;
        this.score = score;
    }

    public Player(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
