package ro.mobile.filescatalogapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ro.mobile.filescatalogapp.entity.File

@Dao
interface FileDAO {

    @Query("SELECT * FROM file_table")
    fun getAll(): LiveData<List<File>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(file: File)
}