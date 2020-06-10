package com.example.eepislibrary.adapter.pemesanan;

public class PemesananAdapterItems {

    private String gambar;
    private String judul;
    private String tgl_pesan;
    private String batas_waktu;

    public PemesananAdapterItems(String gambar, String judul, String tgl_pesan, String batas_waktu) {
        this.gambar = gambar;
        this.judul = judul;
        this.tgl_pesan = tgl_pesan;
        this.batas_waktu = batas_waktu;
    }

    public String getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }

    public String getTgl_pesan() {
        return tgl_pesan;
    }

    public String getBatas_waktu() {
        return batas_waktu;
    }
}
