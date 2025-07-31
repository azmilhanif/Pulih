// JournalEntry.java
package com.nai.pulih.JournalAccess;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "journal_entries")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String title;
    public String body;
}
