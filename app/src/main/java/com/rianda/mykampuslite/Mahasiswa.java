package com.rianda.mykampuslite;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        tableName = "mahasiswa",
        indices = {@Index(value = {"nim"}, unique = true)}
)
public class Mahasiswa extends Person implements EvaluasiAkademik {
    private String nim;
    private String jurusan;
    private double ipk;

    public Mahasiswa() {
        super();
    }

    @Override
    public String getRole() {
        return "Mahasiswa";
    }

    @Override
    public String getStatusAkademik() {
        if (ipk > 3.5) {
            return "Cumlaude";
        } else if (ipk > 3.0) {
            return "Memuaskan";
        } else if (ipk < 2.0) {
            return "Peringatan";
        } else {
            return "Sangat Memuaskan";
        }
    }

    public String getNim() { return nim; }

    public void setNim(String nim) {
        if (nim == null || !nim.matches("\\d{10}")) {
            throw new IllegalArgumentException("NIM harus 10 digit angka");
        }
        this.nim = nim.trim();
    }

    public String getJurusan() { return jurusan; }

    public void setJurusan(String jurusan) {
        if (jurusan == null || jurusan.trim().isEmpty()) {
            throw new IllegalArgumentException("Jurusan tidak boleh kosong");
        }
        this.jurusan = jurusan.trim();
    }

    public double getIpk() { return ipk; }

    public void setIpk(double ipk) {
        if (ipk < 0.0 || ipk > 4.0) {
            throw new IllegalArgumentException("IPK harus berada di rentang 0.0 - 4.0");
        }
        this.ipk = ipk;
    }
}