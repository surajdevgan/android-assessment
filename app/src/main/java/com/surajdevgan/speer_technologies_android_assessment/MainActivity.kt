package com.surajdevgan.speer_technologies_android_assessment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var userRV: RecyclerView
    lateinit var userModalArrayList: ArrayList<UserModel>
    lateinit var tempArrayList: ArrayList<UserModel>
    lateinit var userRVAdapter: UserAdapter
    var BASE_URL = "https://api.github.com/users/"
    var tempsearchurl = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userRV = findViewById(R.id.rcView);
        userRV.layoutManager = LinearLayoutManager(this)
        //  userRV.setHasFixedSize(true)

        userModalArrayList = arrayListOf<UserModel>()
        tempArrayList = ArrayList()
        userRVAdapter = UserAdapter(tempArrayList, this)


// on below line we are initializing our list
        //  userModalArrayList = ArrayList()



// on below line we are initializing our adapter.
        // userRVAdapter = UserAdapter(userModalArrayList)

// on below line we are setting
// adapter to recycler view.


        //  getDataFromAPI()




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{


            override fun onQueryTextSubmit(query: String?): Boolean {
                tempArrayList.clear()
                tempsearchurl = ""

                val searchText = query!!.lowercase(Locale.getDefault())
                tempsearchurl = BASE_URL + searchText
                //   BASE_URL += searchText
                Log.v("Baseurlis",BASE_URL)
                getDataFromAPI()

                tempArrayList.clear()


                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {

                return false

            }


        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getDataFromAPI() {

        tempArrayList.clear()
        userModalArrayList.clear()

// on below line we are creating a
// variable for our request queue
        val queue = Volley.newRequestQueue(this)


// on below line we are creating a request
// variable for making our json object request.
        val request = StringRequest(Request.Method.GET, tempsearchurl,
            { response ->
                tempArrayList.clear()
// this method is called when we get successful response from API.
                try {
                    val `object` = JSONObject(response)
                    Log.v("ddres",response)
                    val username = `object`.getString("name")
                    //Log.v("avturl",""+Email)


                    val id = `object`.getString("login")
                    val ImageUrl = `object`.getString("avatar_url")
                    val Description = `object`.getString("bio")
                    val Following = `object`.getString("following")
                    val Followers = `object`.getString("followers")



                    userModalArrayList.add(UserModel(id, username,Description,
                        "Followers :$Followers", "Following : $Following",ImageUrl))



                    tempArrayList.addAll(userModalArrayList)


                    userRVAdapter.notifyDataSetChanged()

                    userRV.adapter = userRVAdapter



                    userRVAdapter.notifyDataSetChanged()







                    userRVAdapter.onFollowersClick = {

                             val intent = Intent(this, DetailActivity::class.java)
                           intent.putExtra("currentUserData", it)
                        intent.putExtra("whichscreen","followers")
                         startActivity(intent)
                    }

                    userRVAdapter.onFollowingClick = {
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("currentUserData", it)
                        intent.putExtra("whichscreen","following")
                        startActivity(intent)

                    }


                    userRVAdapter.onImageClick = {

                        Toast.makeText(this,"ImageClick",Toast.LENGTH_SHORT).show()
                    }





                } catch (e: Exception) {

                    Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show()

                }



            }, {

                Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show()

            })
        queue.add(request)
    }

}