package ro.mobile.filescatalogapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ro.mobile.filescatalogapp.dao.FileDAO
import ro.mobile.filescatalogapp.entity.File

@Database(entities = arrayOf(File::class), version = 1, exportSchema = false)
public abstract class FileRoomDatabase: RoomDatabase() {

    abstract fun fileDAO(): FileDAO

    private class FileDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.fileDAO()) }
            }
        }

        suspend fun populateDatabase(fileDAO: FileDAO) {

        }
    }

    companion object{
        @Volatile
        private var INSTANCE: FileRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FileRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            } else {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileRoomDatabase::class.java,
                    "file_database"
                )
                    .addCallback(FileDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}