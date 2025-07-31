package com.nai.pulih.JournalAccess;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {JournalEntry.class}, version = 1)
public abstract class JournalDatabase extends RoomDatabase {

    private static volatile JournalDatabase INSTANCE;

    public abstract JournalDao journalDao();

    public static JournalDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (JournalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    JournalDatabase.class, "journal_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
