package com.example.yoelpc.postpc_e2;

import java.util.Date;

public class Message {
    private String author;
    private String content;
    private String timestamp;

    Message(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = new Date().toString();
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
