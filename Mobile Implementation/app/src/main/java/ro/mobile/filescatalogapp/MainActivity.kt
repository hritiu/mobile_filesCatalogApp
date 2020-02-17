package ro.mobile.filescatalogapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ro.mobile.filescatalogapp.activity.NewFileActivity
import ro.mobile.filescatalogapp.entity.File
import ro.mobile.filescatalogapp.ui.FileListAdapter
import ro.mobile.filescatalogapp.ui.FileViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var fileViewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FileListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fileViewModel = ViewModelProvider(this).get(FileViewModel::class.java)
//        fileViewModel.allFiles.observe(this, Observer { files ->
//            files?.let { adapter.setFiles(it) }
//        })
        fileViewModel.allFilesFromServer

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, NewFileActivity::class.java)
            startActivityForResult(intent, NEW_FILE_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_FILE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val fileName = data?.getStringExtra(FILE_NAME)
            val fileStatus = data?.getStringExtra(FILE_STATUS)
            val fileSize = data?.getStringExtra(FILE_SIZE)
            val fileLocation = data?.getStringExtra(FILE_LOCATION)
            val fileUsage = data?.getStringExtra(FILE_USAGE)

            val file = File(fileName.toString(), fileStatus.toString(), fileSize.toString().toInt(), fileLocation.toString(), fileUsage.toString().toInt())
            fileViewModel.insertFile(file)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.hint_new_file_empty_field,
                Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        const val NEW_FILE_ACTIVITY_REQUEST_CODE = 1
        const val FILE_NAME = "FILE_NAME"
        const val FILE_STATUS = "FILE_STATUS"
        const val FILE_SIZE = "FILE_SIZE"
        const val FILE_LOCATION = "FILE_LOCATION"
        const val FILE_USAGE = "FILE_USAGE"
    }
}
