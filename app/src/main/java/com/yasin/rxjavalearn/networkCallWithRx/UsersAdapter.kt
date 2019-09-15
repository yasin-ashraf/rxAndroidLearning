package com.yasin.rxjavalearn.networkCallWithRx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yasin.rxjavalearn.R

class UsersAdapter(private val users : MutableList<User>) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item,parent,false)
        return UsersViewHolder(view)
    }

    fun addUser(user: User) {
        users.add(user)
        notifyItemInserted(users.size - 1)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user : User = users[position]
        holder.name.text = user.name
        holder.email.text = user.email
        holder.company.text = user.company.name
    }

    class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.name)
        val email : TextView = view.findViewById(R.id.email)
        val company : TextView = view.findViewById(R.id.company)
    }
}