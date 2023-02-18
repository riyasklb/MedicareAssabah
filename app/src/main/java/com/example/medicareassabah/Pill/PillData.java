package com.example.medicareassabah.Pill;

public class PillData {

    private String id,medname,dose,date,prescribedays,image,userid;

    public PillData(String id, String medname, String dose, String date, String prescribedays, String image, String userid) {
        this.id = id;
        this.medname = medname;
        this.dose = dose;
        this.date = date;
        this.prescribedays = prescribedays;
        this.image = image;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public String getMedname() {
        return medname;
    }

    public String getDose() {
        return dose;
    }

    public String getDate() {
        return date;
    }

    public String getPrescribedays() {
        return prescribedays;
    }

    public String getImage() {
        return image;
    }

    public String getUserid() {
        return userid;
    }
}