package com.example.job.m_fugo.vetModels;

/**
 * Created by JOB on 7/7/2017.
 */

public class Milk {
    String cow;
    String cow_id;
    String dob;
    String date;
    String disease;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    String medicine;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    String quantity;

    public String getCow() {
        return cow;
    }

    public void setCow(String cow) {
        this.cow = cow;
    }

    public String getCow_id() {
        return cow_id;
    }

    public void setCow_id(String cow_id) {
        this.cow_id = cow_id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLast_record() {
        return last_record;
    }

    public void setLast_record(String last_record) {
        this.last_record = last_record;
    }

    String last_record;
}
