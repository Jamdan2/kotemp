package kotemp.utils

import java.io.File

fun createHtmlFile(rootFolder: String, route: String): File {
    val fileName = if (route == "/") "index" else route
    return File(rootFolder, "$fileName.html")
}


