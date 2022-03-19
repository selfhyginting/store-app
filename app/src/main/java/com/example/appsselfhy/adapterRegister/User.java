package com.example.appsselfhy.adapterRegister;

public class User {
    private String name;
    private String messageReview;
    private String id;
    public User(){};
    public User(String id, String name, String messageReview){
        this.id = id;
        this.name = name;
        this.messageReview = messageReview;
    }
    public String getId() {
        return id;
    }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getmessageReview() {
        return messageReview;
    }
    public void setmessageReview(String messageReview) {
        this.messageReview = messageReview;
    }
}
