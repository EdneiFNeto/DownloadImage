package com.example.updateapp.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.updateapp.R
import com.example.updateapp.util.FileUtil
import com.google.android.material.appbar.MaterialToolbar

class GaleriaActivity : AppCompatActivity() {

    private var galeryAdapter: GaleryAdapter? = null
    private val imagens = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)
        galeryAdapter = GaleryAdapter(imagens)

        findViewById<RecyclerView>(R.id.recycleview).apply {
            adapter = galeryAdapter
        }

        findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            finish()
        }

        getImages()
    }

    inner class GaleryAdapter(private val galerys: ArrayList<Bitmap>) :
        RecyclerView.Adapter<GaleryAdapter.Holder>() {

        inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val images = itemView.findViewById<ImageView>(R.id.imageCard)
            fun add(bitmap: Bitmap) {
                images.setImageBitmap(bitmap)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(
                LayoutInflater.from(this@GaleriaActivity)
                    .inflate(R.layout.liste_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return galerys.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            if (galerys[position] != null)
                holder.add(galerys[position])
        }
    }

    private fun getImages() {
        var image = FileUtil(this).getImage()
        if (image?.isNotEmpty() == true) {
            image?.forEach { bitmap ->
                imagens.add(bitmap)
            }
        }
        galeryAdapter?.notifyDataSetChanged()
    }
}