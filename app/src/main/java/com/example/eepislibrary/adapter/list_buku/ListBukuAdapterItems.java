package com.example.eepislibrary.adapter.list_buku;

public class ListBukuAdapterItems {

    private String gambar;
    private String judul;

    public ListBukuAdapterItems(String gambar, String judul) {
        this.gambar = gambar;
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }
}
