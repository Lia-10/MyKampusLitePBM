package com.rianda.mykampuslite;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        tableName = "dosen",
        indices = {@Index(value = {"nidn"}, unique = true)}
)
public class Dosen extends Person implements EvaluasiAkademik {
    private String nidn;
    private String mataKuliahDiampu;

    public Dosen() {
        super();
    }

    @Override
    public String getRole() {
        return "Dosen";
    }

    @Override
    public String getStatusAkademik() {
        if (mataKuliahDiampu == null || mataKuliahDiampu.trim().isEmpty()) {
            return "Normal";
        }
        String[] matkul = mataKuliahDiampu.split("[,;]");
        int count = 0;
        for (String m : matkul) {
            if (!m.trim().isEmpty()) count++;
        }
        return count > 3 ? "Beban Lebih" : "Normal";
    }

    public String getNidn() { return nidn; }

    public void setNidn(String nidn) {
        if (nidn == null || nidn.trim().length() < 5) {
            throw new IllegalArgumentException("NIDN tidak boleh kosong dan minimal 5 karakter");
        }
        this.nidn = nidn.trim();
    }

    public String getMataKuliahDiampu() { return mataKuliahDiampu; }

    public void setMataKuliahDiampu(String mataKuliahDiampu) {
        if (mataKuliahDiampu == null || mataKuliahDiampu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mata kuliah diampu tidak boleh kosong");
        }
        this.mataKuliahDiampu = mataKuliahDiampu.trim();
    }
}