package com.ismatul.booking_futsal.data;

public class Data {
    private String id, nama, tanggal,jam, lapangan;

    public Data() {
    }

    public Data(String id, String nama, String tanggal,String jam,String lapangan) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.jam = jam;
        this.lapangan = lapangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }


    public String getLapangan() {
        return lapangan;
    }

    public void setLapangan(String lapangan) {
        this.lapangan = lapangan;
    }
}