package com.rafiq.newxplore.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafiq.newxplore.Data.Config.COVER_IMAGE_BASE
import com.rafiq.newxplore.R
import com.rafiq.newxplore.activity.BlogBodyActivity
import com.rafiq.newxplore.activity.MainActivity
import com.rafiq.newxplore.model.TitleModel


class TitlesAdapter(val userList: MutableList<TitleModel>): RecyclerView.Adapter<TitlesAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tv_title.text=userList[position].title
        Glide.with(holder.itemView.context).load(COVER_IMAGE_BASE+userList[position].cover_photo).into(holder.image)
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.tv_title.context, BlogBodyActivity::class.java)
            intent.putExtra("id", userList[position].id)
            intent.putExtra("name", userList[position].title)

            holder.tv_title.context.startActivity(intent)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.single_line_title_adapter, parent, false)
        return ViewHolder(v);
    }


    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val image = itemView.findViewById<ImageView>(R.id.image)

    }

}