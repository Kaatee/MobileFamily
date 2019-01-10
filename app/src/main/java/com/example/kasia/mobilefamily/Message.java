package com.example.kasia.mobilefamily;


public class Message {

        private String message;
        private int sender;
        private String createdAt;

        public Message (String message,int sender , String createdAt){
            this.message=message;
            this.sender=sender;
            this.createdAt=createdAt;
        }

    public String getMessage() {
        return message;
    }

    public int getSender() {
        return sender;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
