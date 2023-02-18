package com.example.medicareassabah.bookings;

public class PbookingDataModel {

    String id, dusername, dhospital, dphone, pusername, booking_date, booking_time, pdisease, mode, payment;

    public PbookingDataModel(String id, String dusername, String dhospital, String dphone, String pusername, String booking_date, String booking_time, String pdisease, String mode, String payment) {
        this.id = id;
        this.dusername = dusername;
        this.dhospital = dhospital;
        this.dphone = dphone;
        this.pusername = pusername;
        this.booking_date = booking_date;
        this.booking_time = booking_time;
        this.pdisease = pdisease;
        this.mode = mode;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public String getDusername() {
        return dusername;
    }

    public String getDhospital() {
        return dhospital;
    }

    public String getDphone() {
        return dphone;
    }

    public String getPusername() {
        return pusername;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public String getPdisease() {
        return pdisease;
    }

    public String getMode() {
        return mode;
    }

    public String getPayment() {
        return payment;
    }
}