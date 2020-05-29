package com.example.eepislibrary.utils.adapter;

public class RiwayatAdapter {

    private String judul;
    private String tgl_pinjam;
    private String tgl_kembali;

    public RiwayatAdapter(String judul, String tgl_pinjam, String tgl_kembali) {
        this.judul = judul;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }
}
