package com.rianda.mykampuslite;

import androidx.lifecycle.LiveData;
import java.util.List;

public interface AkademikRepository {
    LiveData<List<Mahasiswa>> getAllMahasiswa();
    LiveData<List<Dosen>> getAllDosen();

    void insertMahasiswa(Mahasiswa mahasiswa, RepositoryCallback callback);
    void updateMahasiswa(Mahasiswa mahasiswa, RepositoryCallback callback);
    void deleteMahasiswa(Mahasiswa mahasiswa, RepositoryCallback callback);

    void insertDosen(Dosen dosen, RepositoryCallback callback);
    void updateDosen(Dosen dosen, RepositoryCallback callback);
    void deleteDosen(Dosen dosen, RepositoryCallback callback);

    interface RepositoryCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}