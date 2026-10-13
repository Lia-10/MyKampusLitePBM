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
public interface DosenDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Dosen dosen);

    @Update
    void update(Dosen dosen);

    @Delete
    void delete(Dosen dosen);

    @Query("SELECT * FROM dosen ORDER BY id DESC")
    LiveData<List<Dosen>> getAllDosen();
}