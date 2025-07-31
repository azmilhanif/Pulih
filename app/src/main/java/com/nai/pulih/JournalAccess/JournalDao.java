package com.nai.pulih.JournalAccess;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JournalDao {

    @Insert
    void insert(JournalEntry entry);

    @Query("SELECT * FROM journal_entries ORDER BY date DESC")
    List<JournalEntry> getAllJournals();

    @Query("SELECT * FROM journal_entries WHERE id = :id LIMIT 1")
    JournalEntry getJournalById(int id);

    // Add other DAO methods as needed (update, delete, etc)
}
