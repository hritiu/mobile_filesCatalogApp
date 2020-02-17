package ro.mobile.filescatalogapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ro.mobile.filescatalogapp.database.FileRoomDatabase
import ro.mobile.filescatalogapp.entity.File
import ro.mobile.filescatalogapp.network.FileApi
import ro.mobile.filescatalogapp.network.FileFromServer
import ro.mobile.filescatalogapp.repository.FileRepository
import java.lang.Exception

class FileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FileRepository
    val allFiles: LiveData<List<File>>
    var allFilesFromServer: LiveData<List<FileFromServer>>? = null
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response
    var resultList: List<FileFromServer>? = null

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        val filesDAO = FileRoomDatabase.getDatabase(application, viewModelScope).fileDAO()
        repository = FileRepository(filesDAO)
        allFiles = repository.allFiles
        getFiles()
    }

    fun insertFile(file: File) = viewModelScope.launch {
        repository.insertFile(file)
    }

    private fun getFiles() {
        coroutineScope.launch {
            var getFilesDeferred = FileApi.retrofitService.getFiles()
            try{
                var listResult = getFilesDeferred.await()
                _response.value = "Success: ${listResult.size} files retrieved"
                resultList = listResult

            }catch (e: Exception){
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}