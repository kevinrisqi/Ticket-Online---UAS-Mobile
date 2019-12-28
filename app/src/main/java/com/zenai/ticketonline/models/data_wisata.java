package com.zenai.ticketonline.models;

public class data_wisata {
    private String nama;
    private String lokasi;
    private String deskripsi;
    private int harga;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public data_wisata(){

    }

    public data_wisata(String nama, String lokasi, String deskripsi, int harga) {
        this.nama = nama;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
