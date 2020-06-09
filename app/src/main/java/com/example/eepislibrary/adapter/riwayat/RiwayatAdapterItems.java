package com.example.eepislibrary.adapter.riwayat;

public class RiwayatAdapterItems {

    private String gambar;
    private String judul;
    private String tgl_pinjam;
    private String tgl_kembali;

    public RiwayatAdapterItems(String gambar, String judul, String tgl_pinjam, String tgl_kembali) {
        this.gambar = gambar;
        this.judul = judul;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
    }

    String getGambar() {
        return gambar;
    }

    String getJudul() {
        return judul;
    }

    String getTgl_pinjam() {
        return tgl_pinjam;
    }

    String getTgl_kembali() {
        return tgl_kembali;
    }
}
