package com.rianda.mykampuslite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MahasiswaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Mahasiswa mahasiswa);

    @Update
    void update(Mahasiswa mahasiswa);

    @Delete
    void delete(Mahasiswa mahasiswa);

    @Query("SELECT * FROM mahasiswa ORDER BY id DESC")
    LiveData<List<Mahasiswa>> getAllMahasiswa();
}