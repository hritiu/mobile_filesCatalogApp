package ro.mobile.filescatalogapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.mobile.filescatalogapp.R
import ro.mobile.filescatalogapp.entity.File
import ro.mobile.filescatalogapp.network.FileFromServer

class FileListAdapter internal constructor(
    context: Context
): RecyclerView.Adapter<FileListAdapter.FileViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var files = emptyList<File>()
    private var filesFromServer = emptyList<FileFromServer>()

    inner class FileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fileNameView: TextView = itemView.findViewById(R.id.file_name)
        val fileStatusView: TextView = itemView.findViewById(R.id.file_status)
        val fileSizeView: TextView = itemView.findViewById(R.id.file_size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
//        val current = files[position]
        val current = filesFromServer[position]
        holder.fileNameView.text = current.name
        holder.fileStatusView.text = current.status
        holder.fileSizeView.text = current.size.toString()
    }

//    internal fun setFiles(files: List<File>) {
//        this.files = files
//        notifyDataSetChanged()
//    }
    internal fun setFiles(files: List<FileFromServer>) {
        this.filesFromServer = files
        notifyDataSetChanged()
    }

    override fun getItemCount() = files.size
}