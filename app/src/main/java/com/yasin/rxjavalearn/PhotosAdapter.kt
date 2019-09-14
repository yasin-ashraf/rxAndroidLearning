package com.yasin.rxjavalearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PhotosAdapter(private val photos: List<String>) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_item,parent,false)
        return PhotosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val uri : String = photos[position]
        Picasso.get().load(uri)
                .error(R.mipmap.ic_launcher)
                .into(holder.image)
    }

    class PhotosViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image : ImageView = view.findViewById(R.id.image)
    }
}