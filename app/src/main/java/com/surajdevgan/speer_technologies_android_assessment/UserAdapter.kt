package com.surajdevgan.speer_technologies_android_assessment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(
    private  val userModalArrayList: ArrayList<UserModel>,
    private val context: Context

    ) :
    RecyclerView.Adapter<UserAdapter.holder>() {

    var onItemClick : ((UserModel) -> Unit)? = null
    var onImageClick:((UserModel) -> Unit)? = null
    var onFollowersClick : ((UserModel) -> Unit)? = null
    var onFollowingClick : ((UserModel) -> Unit)? = null





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_rv_item, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val  userModal   = userModalArrayList[position]

        holder.username.text = userModal.username
        holder.description.text = userModal.description
        holder.followers.text = userModal.followers
        holder.following.text = userModal.following



        // on below line we are loading our image
        // from url in our image view using glide.
        Glide.with(context).load(userModal.avatar).into(holder.userIV)


        holder.userIV.setOnClickListener{
            onImageClick?.invoke(userModal)


        }

        holder.followers.setOnClickListener {
            onFollowersClick?.invoke(userModal)


        }

        holder.following.setOnClickListener {

            onFollowingClick?.invoke(userModal)

        }

              holder.itemView.setOnClickListener {
           onItemClick?.invoke(userModal)

}

    }

    override fun getItemCount(): Int {
        return userModalArrayList.size

    }

    class holder(itemView: View) : RecyclerView.ViewHolder (itemView){

        val  username : TextView = itemView.findViewById(R.id.user_name)
        val  description : TextView = itemView.findViewById(R.id.user_bio)
        val  followers : TextView = itemView.findViewById(R.id.user_followers)
        val  following : TextView = itemView.findViewById(R.id.user_following)


        val  userIV : ImageView = itemView.findViewById(R.id.user_image)


    }



}