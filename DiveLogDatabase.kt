package com.example.divelogger

        import android.content.Context
        import androidx.room.Database
        import androidx.room.Room
        import androidx.room.RoomDatabase

        @Database(entities = [DiveLog::class], version = 1)
        abstract class DiveLogDatabase : RoomDatabase() {
            abstract fun diveLogDao(): DiveLogDao

            companion object {
                @Volatile
                private var INSTANCE: DiveLogDatabase? = null

                fun getDatabase(context: Context): DiveLogDatabase {
                    return INSTANCE ?: synchronized(this) {
                        val instance = Room.databaseBuilder(
                            context.applicationContext,
                            DiveLogDatabase::class.java,
                            "divelog_db"
                        ).build()
                        INSTANCE = instance
                        instance
                    }
                }
            }
        }