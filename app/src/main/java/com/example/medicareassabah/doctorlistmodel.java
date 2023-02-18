package com.example.medicareassabah;

public class doctorlistmodel {
    private String username,department,qualification,experience,hospital,stime,etime,available_day,phone,payment,status;


    public doctorlistmodel(String username, String department, String qualification, String experience,
                           String hospital, String stime,String etime, String available_day, String phone, String payment, String status) {
        this.username = username;
        this.department = department;
        this.qualification = qualification;
        this.experience = experience;
        this.hospital = hospital;
        this.stime = stime;
        this.etime = etime;
        this.available_day = available_day;
        this.phone = phone;
        this.payment = payment;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getDepartment() {
        return department;
    }

    public String getQualification() {
        return qualification;
    }

    public String getExperience() {
        return experience;
    }

    public String getHospital() {
        return hospital;
    }

    public String getStime() {
        return stime;
    }
    public String getEtime() {
        return etime;
    }


    public String getAvailable_day() {
        return available_day;
    }

    public String getPhone() {
        return phone;
    }

    public String getPayment() {
        return payment;
    }

    public String getStatus() {
        return status;
    }
}


