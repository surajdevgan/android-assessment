package com.surajdevgan.speer_technologies_android_assessment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowersAdapter(
    private  val userModalArrayList: ArrayList<Followmodel>,
    private val context: Context

) :
    RecyclerView.Adapter<FollowersAdapter.holder>() {

    var onItemClick : ((Followmodel) -> Unit)? = null
    var onImageClick : ((Followmodel) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.follow_rc_view, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val  userModal   = userModalArrayList[position]

        holder.username.text = userModal.username
        holder.email.text = userModal.useremail

        // on below line we are loading our image
        // from url in our image view using glide.
        Glide.with(context).load(userModal.avatar).into(holder.userIV)


        holder.userIV.setOnClickListener{
            onImageClick?.invoke(userModal)


        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(userModal)

        }

    }

    override fun getItemCount(): Int {
        return userModalArrayList.size

    }

    class holder(itemView: View) : RecyclerView.ViewHolder (itemView){

        val  username : TextView = itemView.findViewById(R.id.idTVFirstName)
        val  email : TextView = itemView.findViewById(R.id.idTVEmail)
        val  userIV : ImageView = itemView.findViewById(R.id.idIVUser)


    }

}