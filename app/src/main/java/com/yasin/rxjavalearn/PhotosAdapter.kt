package com.yasin.rxjavalearn

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PhotosAdapter(private val photos: MutableList<String>) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_item,parent,false)
        return PhotosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addPhotos(string: String) {
        photos.add(string)
        notifyItemInserted(photos.size - 1)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val uri : String = photos[position]
        Picasso.get().load("file://$uri")
                .error(R.mipmap.ic_launcher)
                .into(holder.image, object : Callback{
                    override fun onSuccess() {
                        Log.d("Picasso", "Success")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("Picasso Error",e.toString())
                    }

                })
    }

    class PhotosViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image : ImageView = view.findViewById(R.id.image)
    }
}