package com.example.divelogger

        import androidx.room.*

        @Dao
        interface DiveLogDao {
            @Query("SELECT * FROM DiveLog ORDER BY id DESC")
            suspend fun getAll(): List<DiveLog>

            @Insert
            suspend fun insert(log: DiveLog)

            @Delete
            suspend fun delete(log: DiveLog)
        }