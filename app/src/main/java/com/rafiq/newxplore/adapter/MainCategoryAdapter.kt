package com.rafiq.newxplore.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafiq.newxplore.Data.Config.CATEGORY_IMAGE_BASE
import com.rafiq.newxplore.R
import com.rafiq.newxplore.activity.SecondLevelCategoryActivity
import com.rafiq.newxplore.model.Category
import com.rafiq.newxplore.model.TitleModel


class MainCategoryAdapter(val userList: MutableList<Category>): RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tv_title.text=userList[position].title
        Glide.with(holder.itemView.context).load(CATEGORY_IMAGE_BASE+userList[position].image).into(holder.img)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.tv_title.context, SecondLevelCategoryActivity::class.java)
            intent.putExtra("id", userList[position].id)
            intent.putExtra("name", userList[position].title)
            holder.tv_title.context.startActivity(intent)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.blog_title_item, parent, false)
        return ViewHolder(v);
    }


    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val img = itemView.findViewById<ImageView>(R.id.img)

    }

}