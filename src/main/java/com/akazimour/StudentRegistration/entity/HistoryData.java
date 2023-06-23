package com.akazimour.StudentRegistration.entity;

import org.hibernate.envers.RevisionType;

import java.util.Date;

public class HistoryData<T> {
    private T data;
    private RevisionType revisionType;
    private Integer revision;
    private Date date;
    public HistoryData() {
    }

    public HistoryData(T data, RevisionType revisionType, Integer revision, Date date) {
        this.data = data;
        this.revisionType = revisionType;
        this.revision = revision;
        this.date = date;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RevisionType getRevisionType() {
        return revisionType;
    }

    public void setRevisionType(RevisionType revisionType) {
        this.revisionType = revisionType;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
