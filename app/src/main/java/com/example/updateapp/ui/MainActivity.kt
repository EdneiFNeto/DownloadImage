package com.example.updateapp.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.updateapp.R
import com.example.updateapp.async.BaseAsyncTask
import com.example.updateapp.async.Carregando
import com.example.updateapp.util.DownloadInputStreamUtil
import com.example.updateapp.util.FileUtil
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private var fileUtil: FileUtil? = null
    private var editTextUrl: EditText? = null
    private val REQUEST_INSTALL = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AddPermissions().requestPermission()

        fileUtil = FileUtil(this).getInstance()

        editTextUrl = findViewById<EditText>(R.id.edt_text_url)
        var textFieldUrl = findViewById<TextInputLayout>(R.id.textFieldUrl)
        downloadAndSaveImage(textFieldUrl)
        startActivitys()
    }

    private fun downloadAndSaveImage(textFieldUrl: TextInputLayout) {
        textFieldUrl.setEndIconOnClickListener {
            if (editTextUrl?.text?.isNotEmpty() == true)
                downloader()
            else {
                textFieldUrl.error = "Field required!"
                Handler().postDelayed({
                    textFieldUrl.error = null
                }, 3000)
            }
        }
    }

    private fun startActivitys() {
        findViewById<MaterialToolbar>(R.id.topAppBar).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_galery -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun downloader() {
        BaseAsyncTask<Bitmap?>(
            executando = {
                DownloadInputStreamUtil()
                    .download("${editTextUrl?.text}")
            }, callBack = object : Carregando {
                override fun carregando() {
                    Toast.makeText(this@MainActivity, "Download...", Toast.LENGTH_SHORT).show()
                }
            }, finalizado = { bitmap ->
                findViewById<ImageView>(R.id.imagePhoto).setImageBitmap(bitmap)
                bitmap?.let {
                    fileUtil?.save(bitmap, "${System.currentTimeMillis()}.jpg")
                }

                editTextUrl?.text = null
            }).execute()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "${permissions[i]} granted")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_INSTALL) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Toast.makeText(this, "Install succeeded!", Toast.LENGTH_SHORT).show();
                }
                Activity.RESULT_CANCELED -> {
                    Toast.makeText(this, "Install canceled!", Toast.LENGTH_SHORT).show();
                }
                else -> {
                    Toast.makeText(this, "Install Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    inner class AddPermissions {

        fun requestPermission() {
            var permissionsToRequest = mutableListOf<String>()
            if (!getPermission()) {
                permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    permissionsToRequest.toTypedArray(),
                    0
                )
            }
        }


        private fun getPermission() = ActivityCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}