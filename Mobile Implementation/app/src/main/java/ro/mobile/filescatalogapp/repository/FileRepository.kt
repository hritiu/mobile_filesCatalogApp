package ro.mobile.filescatalogapp.repository

import androidx.lifecycle.LiveData
import ro.mobile.filescatalogapp.dao.FileDAO
import ro.mobile.filescatalogapp.entity.File

class FileRepository(private val fileDAO: FileDAO) {

    val allFiles: LiveData<List<File>> = fileDAO.getAll()

    suspend fun insertFile(file: File) {
        fileDAO.insert(file)
    }
}