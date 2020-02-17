package ro.mobile.filescatalogapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file_table")
class File(
    @ColumnInfo(name = "fileName") val name: String,
    val status: String,
    val size: Int,
    val location: String,
    val usage: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}