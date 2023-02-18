package com.example.medicareassabah;

public class FileData {

    private String id, user, filename, record, key;

    public FileData(String id, String user, String filename, String record, String key) {
        this.id = id;
        this.user = user;
        this.filename = filename;
        this.record = record;
        this.key = key;
    }

    public String getId() {
        return id;
    }
    public String getUser() {
        return user;
    }

    public String getFilename() {
        return filename;
    }

    public String getRecord() {
        return record;
    }

    public String getKey() {
        return key;
    }
}
