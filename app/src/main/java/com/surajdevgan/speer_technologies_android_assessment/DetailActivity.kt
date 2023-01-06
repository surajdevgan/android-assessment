package com.surajdevgan.speer_technologies_android_assessment

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.math.log


class DetailActivity : AppCompatActivity() {

    lateinit var userRV: RecyclerView
    lateinit var followModalArrayList: ArrayList<Followmodel>
    lateinit var tempArrayList: ArrayList<Followmodel>
    lateinit var userRVAdapter: FollowersAdapter
    var BASE_URL = "https://api.github.com/users/"
    var tempsearchurl = ""




    lateinit var userName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val userData = intent.getSerializableExtra("currentUserData") as UserModel?
        val ss:String = intent.getStringExtra("whichscreen").toString()



        userRV = findViewById(R.id.rcViewfollow);
        userRV.layoutManager = LinearLayoutManager(this)
        //  userRV.setHasFixedSize(true)

        followModalArrayList = arrayListOf<Followmodel>()
        tempArrayList = ArrayList()
        userRVAdapter = FollowersAdapter(tempArrayList, this)


// on below line we are initializing our list
        //  userModalArrayList = ArrayList()



// on below line we are initializing our adapter.
        // userRVAdapter = UserAdapter(userModalArrayList)

// on below line we are setting
// adapter to recycler view.
        if(userData!=null && ss.isNotEmpty())
        {

            val actionBar = supportActionBar
            actionBar!!.title = ss


            tempsearchurl = BASE_URL + userData.userid + "/" + ss
            Log.v("uui", tempsearchurl)

            getDataFromAPI()

        }

    }



    private fun getDataFromAPI() {


// on below line we are creating a
// variable for our request queue
        val queue = Volley.newRequestQueue(this)


// on below line we are creating a request
// variable for making our json object request.
        val request = StringRequest(Request.Method.GET, tempsearchurl,
            { response ->
// this method is called when we get successful response from API.
                try {
                    val jsonArrayyy = JSONArray(response)

                    for (i in 0 until jsonArrayyy.length()) {
                        val jsonObject = jsonArrayyy.getJSONObject(i)
                        val Email = jsonObject.getString("html_url")
                        Log.v("iiiii",Email)
                        val ImageUrl = jsonObject.getString("avatar_url")
                        val username = jsonObject.getString("login")

                        followModalArrayList.add(Followmodel(Email,username,ImageUrl))
                    }

                    tempArrayList.addAll(followModalArrayList)


                    userRVAdapter.notifyDataSetChanged()

                    userRV.adapter = userRVAdapter



                    userRVAdapter.notifyDataSetChanged()



                    userRVAdapter.onItemClick = {

                     //   val intent = Intent(this, DetailsActivity::class.java)
                       // intent.putExtra("currentUserData", it)
                       // startActivity(intent)
                    }


                    userRVAdapter.onImageClick = {

                        Toast.makeText(this,"ImageClick",Toast.LENGTH_SHORT).show()
                    }




// on below line we
// are handling exception
                } catch (e: Exception) {
                    Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()

                }

            }, {

                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()

            })
        queue.add(request)
    }

}