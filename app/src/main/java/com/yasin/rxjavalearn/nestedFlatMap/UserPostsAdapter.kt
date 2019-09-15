package com.yasin.rxjavalearn.nestedFlatMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yasin.rxjavalearn.R

class UserPostsAdapter(private val posts : MutableList<Post>) : RecyclerView.Adapter<UserPostsAdapter.UserPostsViewHolder>() {

    fun addPost(post: Post) {
        posts.add(post)
        notifyItemInserted(posts.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item,parent,false)
        return UserPostsViewHolder(view = view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: UserPostsViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.title
        holder.body.text = post.body
    }

    class UserPostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val body : TextView = view.findViewById(R.id.body)
    }
}