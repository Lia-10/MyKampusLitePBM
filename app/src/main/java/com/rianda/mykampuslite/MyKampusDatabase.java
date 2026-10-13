package com.rianda.mykampuslite;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Mahasiswa.class, Dosen.class}, version = 1, exportSchema = false)
public abstract class MyKampusDatabase extends RoomDatabase {

    private static volatile MyKampusDatabase INSTANCE;

    public abstract MahasiswaDao mahasiswaDao();
    public abstract DosenDao dosenDao();

    public static MyKampusDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyKampusDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MyKampusDatabase.class,
                            "mykampus_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}