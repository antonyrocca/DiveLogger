package com.example.divelogger

        import androidx.room.Entity
        import androidx.room.PrimaryKey

        @Entity
        data class DiveLog(
            @PrimaryKey(autoGenerate = true) val id: Int = 0,
            val location: String,
            val depth: Int,
            val duration: Int,
            val temperature: Float,
            val weights: Float,
            val notes: String
        )