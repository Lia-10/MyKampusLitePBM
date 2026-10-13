package com.rianda.mykampuslite;

public abstract class Person extends BaseEntity {
    private String nama;
    private String email;

    public abstract String getRole();

    public String getNama() { return nama; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().length() < 3) {
            throw new IllegalArgumentException("Nama tidak boleh kosong dan minimal 3 karakter");
        }
        this.nama = nama.trim();
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email tidak valid (harus mengandung '@')");
        }
        this.email = email.trim();
    }
}