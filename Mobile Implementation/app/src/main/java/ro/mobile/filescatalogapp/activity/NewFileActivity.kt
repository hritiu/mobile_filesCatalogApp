package ro.mobile.filescatalogapp.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import ro.mobile.filescatalogapp.R

class NewFileActivity : AppCompatActivity() {

    private lateinit var editFileNameView: EditText
    private lateinit var editFileStatusView: EditText
    private lateinit var editFileSizeView: EditText
    private lateinit var editFileLocationView: EditText
    private lateinit var editFileUsageView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_file)
        editFileNameView = findViewById(R.id.edit_file_name)
        editFileStatusView = findViewById(R.id.edit_file_status)
        editFileSizeView = findViewById(R.id.edit_file_size)
        editFileLocationView = findViewById(R.id.edit_file_location)
        editFileUsageView = findViewById(R.id.edit_file_usage)

        val saveButton = findViewById<Button>(R.id.new_file_button_save)
        saveButton.setOnClickListener{
            val replyIntent = Intent()
            if(TextUtils.isEmpty(editFileNameView.text) || TextUtils.isEmpty(editFileStatusView.text) || TextUtils.isEmpty(editFileSizeView.text)
                || TextUtils.isEmpty(editFileLocationView.text) || TextUtils.isEmpty(editFileUsageView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val fileName = editFileNameView.text.toString()
                val fileStatus = editFileStatusView.text.toString()
                val fileSize = editFileSizeView.text.toString()
                val fileLocation = editFileLocationView.text.toString()
                val fileUsage = editFileUsageView.text.toString()


                replyIntent.putExtra("FILE_NAME", fileName)
                replyIntent.putExtra("FILE_STATUS", fileStatus)
                replyIntent.putExtra("FILE_SIZE", fileSize)
                replyIntent.putExtra("FILE_LOCATION", fileLocation)
                replyIntent.putExtra("FILE_USAGE", fileUsage)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}
