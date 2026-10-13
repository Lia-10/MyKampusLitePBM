package com.rianda.mykampuslite;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AkademikRepositoryImpl implements AkademikRepository {

    private final MahasiswaDao mahasiswaDao;
    private final DosenDao dosenDao;
    private final ExecutorService executorService;

    public AkademikRepositoryImpl(Application application) {
        MyKampusDatabase db = MyKampusDatabase.getInstance(application);
        this.mahasiswaDao = db.mahasiswaDao();
        this.dosenDao = db.dosenDao();
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public LiveData<List<Mahasiswa>> getAllMahasiswa() {
        return mahasiswaDao.getAllMahasiswa();
    }

    @Override
    public LiveData<List<Dosen>> getAllDosen() {
        return dosenDao.getAllDosen();
    }

    @Override
    public void insertMahasiswa(Mahasiswa mahasiswa, RepositoryCallback callback) {
        executorService.execute(() -> {
            try {
                long result = mahasiswaDao.insert(mahasiswa);
                if (result != -1) {
                    if (callback != null) callback.onSuccess("Data Mahasiswa berhasil disimpan");
                } else {
                    if (callback != null) callback.onError("Gagal menyimpan: NIM sudah terdaftar!");
                }
            } catch (Exception e) {
                if (callback != null) callback.onError("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void updateMahasiswa(Mahasiswa mahasiswa, RepositoryCallback callback) {
        executorService.execute(() -> {
            try {
                mahasiswaDao.update(mahasiswa);
                if (callback != null) callback.onSuccess("Data Mahasiswa berhasil diperbarui");
            } catch (Exception e) {
                if (callback != null) callback.onError("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void deleteMahasiswa(Mahasiswa mahasiswa, RepositoryCallback callback) {
        executorService.execute(() -> {
            try {
                mahasiswaDao.delete(mahasiswa);
                if (callback != null) callback.onSuccess("Data Mahasiswa berhasil dihapus");
            } catch (Exception e) {
                if (callback != null) callback.onError("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void insertDosen(Dosen dosen, RepositoryCallback callback) {
        executorService.execute(() -> {
            try {
                long result = dosenDao.insert(dosen);
                if (result != -1) {
                    if (callback != null) callback.onSuccess("Data Dosen berhasil disimpan");
                } else {
                    if (callback != null) callback.onError("Gagal menyimpan: NIDN sudah terdaftar!");
                }
            } catch (Exception e) {
                if (callback != null) callback.onError("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void updateDosen(Dosen dosen, RepositoryCallback callback) {
        executorService.execute(() -> {
            try {
                dosenDao.update(dosen);
                if (callback != null) callback.onSuccess("Data Dosen berhasil diperbarui");
            } catch (Exception e) {
                if (callback != null) callback.onError("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void deleteDosen(Dosen dosen, RepositoryCallback callback) {
        executorService.execute(() -> {
            try {
                dosenDao.delete(dosen);
                if (callback != null) callback.onSuccess("Data Dosen berhasil dihapus");
            } catch (Exception e) {
                if (callback != null) callback.onError("Error: " + e.getMessage());
            }
        });
    }
}