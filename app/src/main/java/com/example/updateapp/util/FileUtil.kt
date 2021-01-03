package com.example.updateapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class FileUtil(private val context: Context) {

    private var fileUtil: FileUtil? = null
    fun getInstance(): FileUtil? {
        if (fileUtil == null) {
            fileUtil = FileUtil(context)
        }

        return fileUtil
    }

    fun save(bitmap: Bitmap, nameFile: String) {
        var file = File("${getPath}/${nameFile}")
        try {
            if (!file.exists()) {
                if (file?.createNewFile()) {
                    Toast.makeText(context, "Success save image!", Toast.LENGTH_LONG).show()
                    Log.i("Success", "Success save image ${file.path}")
                    var stream = FileOutputStream(file)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.close()
                } else {
                    Log.e("Failed", "Failed save image")
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete() {
        var file = File("$getPath")
        var listFiles = file.listFiles()
        try {
            Log.i("Success", "Path ${file.path}")
            Log.i("Success", "List $listFiles")

            if (listFiles.isNotEmpty()) {
                for (i in listFiles.indices) {
                    listFiles[i].delete()
                }
            } else {
                Log.i("Failed", "Not exists file $listFiles")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getImage(): ArrayList<Bitmap>? {

        var images = arrayListOf<Bitmap>()
        try {

            var files = File("$getPath")
            var listFiles = files.listFiles()
            var bitmap: Bitmap? = null

            for (file in listFiles) {
                Log.i("Files existe", "${file.path}")
                bitmap = BitmapFactory.decodeFile("${getPath}/${file.name}")
                images.add(bitmap)
            }

            return images
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return arrayListOf()
    }

    private val getPath = context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
}
