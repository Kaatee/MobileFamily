package com.example.kasia.mobilefamily;



public class Message {

        String message;
        int sender;
        long createdAt;

        public Message (String message,int sender , long createdAt){
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

    public long getCreatedAt() {
        return createdAt;
    }
}
