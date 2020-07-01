package com.example.eepislibrary.adapter.ebook;

public class ListEbookAdapterItems {

    private String id;
    private String gambar;
    private String judul;
    private String ebook;

    public ListEbookAdapterItems(String id, String gambar, String judul, String ebook) {
        this.id = id;
        this.gambar = gambar;
        this.judul = judul;
        this.ebook = ebook;
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

    public String getEbook(){
        return ebook;
    }
}
