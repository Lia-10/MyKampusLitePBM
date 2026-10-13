package com.rianda.mykampuslite;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class AkademikViewModel extends AndroidViewModel {

    // ViewModel hanya bergantung pada Interface AkademikRepository (Dependency Inversion)
    private final AkademikRepository repository;

    private final LiveData<List<Mahasiswa>> allMahasiswa;
    private final LiveData<List<Dosen>> allDosen;

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AkademikViewModel(@NonNull Application application) {
        super(application);
        this.repository = new AkademikRepositoryImpl(application);
        this.allMahasiswa = repository.getAllMahasiswa();
        this.allDosen = repository.getAllDosen();
    }

    public LiveData<List<Mahasiswa>> getAllMahasiswa() { return allMahasiswa; }
    public LiveData<List<Dosen>> getAllDosen() { return allDosen; }
    public LiveData<String> getToastMessage() { return toastMessage; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    public boolean saveMahasiswa(long id, String nama, String email, String nim, String jurusan, String ipkStr) {
        try {
            double ipk;
            try {
                ipk = Double.parseDouble(ipkStr);
            } catch (NumberFormatException e) {
                errorMessage.setValue("IPK harus berupa angka yang valid!");
                return false;
            }

            Mahasiswa m = new Mahasiswa();
            if (id > 0) m.setId(id);

            // Trigger setter validation (Encapsulation)
            m.setNama(nama);
            m.setEmail(email);
            m.setNim(nim);
            m.setJurusan(jurusan);
            m.setIpk(ipk);
            m.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

            AkademikRepository.RepositoryCallback callback = new AkademikRepository.RepositoryCallback() {
                @Override
                public void onSuccess(String message) { toastMessage.postValue(message); }

                @Override
                public void onError(String error) { errorMessage.postValue(error); }
            };

            if (id > 0) {
                repository.updateMahasiswa(m, callback);
            } else {
                repository.insertMahasiswa(m, callback);
            }
            return true;
        } catch (IllegalArgumentException e) {
            errorMessage.setValue(e.getMessage());
            return false;
        }
    }

    public boolean saveDosen(long id, String nama, String email, String nidn, String mataKuliahDiampu) {
        try {
            Dosen d = new Dosen();
            if (id > 0) d.setId(id);

            // Trigger setter validation (Encapsulation)
            d.setNama(nama);
            d.setEmail(email);
            d.setNidn(nidn);
            d.setMataKuliahDiampu(mataKuliahDiampu);
            d.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

            AkademikRepository.RepositoryCallback callback = new AkademikRepository.RepositoryCallback() {
                @Override
                public void onSuccess(String message) { toastMessage.postValue(message); }

                @Override
                public void onError(String error) { errorMessage.postValue(error); }
            };

            if (id > 0) {
                repository.updateDosen(d, callback);
            } else {
                repository.insertDosen(d, callback);
            }
            return true;
        } catch (IllegalArgumentException e) {
            errorMessage.setValue(e.getMessage());
            return false;
        }
    }

    public void deleteMahasiswa(Mahasiswa m) {
        repository.deleteMahasiswa(m, new AkademikRepository.RepositoryCallback() {
            @Override
            public void onSuccess(String message) { toastMessage.postValue(message); }

            @Override
            public void onError(String error) { errorMessage.postValue(error); }
        });
    }

    public void deleteDosen(Dosen d) {
        repository.deleteDosen(d, new AkademikRepository.RepositoryCallback() {
            @Override
            public void onSuccess(String message) { toastMessage.postValue(message); }

            @Override
            public void onError(String error) { errorMessage.postValue(error); }
        });
    }
}