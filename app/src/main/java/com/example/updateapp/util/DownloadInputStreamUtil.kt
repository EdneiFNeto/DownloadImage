package com.example.updateapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadInputStreamUtil {

    fun download(imagem: String?): Bitmap? {
        try {
            var url = URL(imagem)
            var urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            var inputStream = urlConnection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        }catch (e: Exception){
            e.printStackTrace()
            Log.e("Download", "Mensagem ${e.message}")
            Log.e("Download", "Cause ${e.cause}")
        }
        return null
    }
}