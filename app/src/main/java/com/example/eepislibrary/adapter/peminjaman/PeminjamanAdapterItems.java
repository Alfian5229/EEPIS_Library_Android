package com.example.eepislibrary.adapter.peminjaman;

public class PeminjamanAdapterItems {

    private String gambar;
    private String judul;
    private String tgl_pinjam;
    private String batas_waktu;

    public PeminjamanAdapterItems(String gambar, String judul, String tgl_pinjam, String batas_waktu) {
        this.gambar = gambar;
        this.judul = judul;
        this.tgl_pinjam = tgl_pinjam;
        this.batas_waktu = batas_waktu;
    }

    public String getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public String getBatas_waktu() {
        return batas_waktu;
    }
}
