package com.codecool.codebook.model;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Student senderStudent;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Student receiverStudent;

    private String textMessage;

    public Message() {
    }

    public Message(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getSenderStudent() {
        return senderStudent;
    }

    public void setSenderStudent(Student senderStudent) {
        this.senderStudent = senderStudent;
    }

    public Student getReceiverStudent() {
        return receiverStudent;
    }

    public void setReceiverStudent(Student receiverStudent) {
        this.receiverStudent = receiverStudent;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
