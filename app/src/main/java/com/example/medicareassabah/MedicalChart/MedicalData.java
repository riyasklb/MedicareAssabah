package com.example.medicareassabah.MedicalChart;

public class MedicalData {

    private String bookdate, doctorname, doctorphn,  department,doctorhospital, diseaseInfo, files,puser;

    public MedicalData(String bookdate, String doctorname, String doctorphn, String department, String doctorhospital, String diseaseInfo, String files, String puser) {
        this.bookdate = bookdate;
        this.doctorname = doctorname;
        this.doctorphn = doctorphn;
        this.department = department;
        this.doctorhospital = doctorhospital;
        this.diseaseInfo = diseaseInfo;
        this.files = files;
        this.puser = puser;
    }

    public String getBookdate() {
        return bookdate;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public String getDoctorphn() {
        return doctorphn;
    }

    public String getDepartment() {
        return department;
    }

    public String getDoctorhospital() {
        return doctorhospital;
    }

    public String getDiseaseInfo() {
        return diseaseInfo;
    }

    public String getFiles() {
        return files;
    }

    public String getPuser() {
        return puser;
    }
}