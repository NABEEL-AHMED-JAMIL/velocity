package com.ballistic.velocity.model.dto;

import com.ballistic.velocity.model.pojo.Document;
import com.google.gson.Gson;

import java.util.Set;

public class EmailDto<T> {

    private Set<String> sendTo;
    private Set<String> ccTo;
    private String subject;
    private T field;
    private Document document;

    public EmailDto() {}

    public EmailDto(Set<String> sendTo, String subject, Document document) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.document = document;
    }

    public EmailDto(Set<String> sendTo, Set<String> ccTo, String subject, Document document) {
        this.sendTo = sendTo;
        this.ccTo = ccTo;
        this.subject = subject;
        this.document = document;
    }

    public Set<String> getSendTo() { return sendTo; }
    public void setSendTo(Set<String> sendTo) { this.sendTo = sendTo; }

    public Set<String> getCcTo() { return ccTo; }
    public void setCcTo(Set<String> ccTo) { this.ccTo = ccTo; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public T getField() { return field; }
    public void setField(T field) { this.field = field; }

    public Document getDocument() { return document; }
    public void setDocument(Document document) { this.document = document; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
