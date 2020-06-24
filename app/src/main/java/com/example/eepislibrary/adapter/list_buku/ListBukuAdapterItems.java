package com.example.eepislibrary.adapter.list_buku;

public class ListBukuAdapterItems {

    private String id;
    private String gambar;
    private String judul;

    public ListBukuAdapterItems(String id, String gambar, String judul) {
        this.id = id;
        this.gambar = gambar;
        this.judul = judul;
    }

    public String getId() {
        return id;
    }

    public String getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }
}
