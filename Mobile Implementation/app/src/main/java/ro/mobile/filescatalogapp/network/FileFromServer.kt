package ro.mobile.filescatalogapp.network

data class FileFromServer(
    val id: Int,
    val name: String,
    val status: String,
    val size: Int,
    val location: String,
    val usage: Int
)