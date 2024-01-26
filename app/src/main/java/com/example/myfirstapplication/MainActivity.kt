// MainActivity.kt
package com.example.myfirstapplication

// Import necessary Android and Kotlin libraries
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Define the MainActivity class which extends AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Declare RecyclerView and SongAdapter as lateinit variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongAdapter

    // Override the onCreate method to set up the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Log a message when the activity is created
        Log.d("MainActivity", "onCreate")

        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView and SongAdapter
        recyclerView = findViewById(R.id.recyclerView)
        adapter = SongAdapter(emptyList())

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Set an item click listener for the adapter
        adapter.setOnItemClickListener { track ->
            // Log the track title for now
            Log.d("MainActivity", "Clicked on track: ${track.title}")
        }

        // Make an API call to fetch data
        fetchData()
    }

    // Define a function to make an API call and update the UI
    private fun fetchData() {
        // Create a Retrofit instance for API communication
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.deezer.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the ApiService interface using Retrofit
        val apiService = retrofit.create(ApiService::class.java)

        // Use Kotlin coroutines to perform the API call asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            // Make the API call to get a list of ten songs
            val response = apiService.getTenSongsList()

            // Switch back to the main thread to update the UI
            withContext(Dispatchers.Main) {
                // Check if the API call was successful
                if (response.isSuccessful) {
                    // Extract the list of tracks from the API response
                    val songs = response.body()?.data ?: emptyList()

                    // Update the adapter with the new list of tracks
                    adapter = SongAdapter(songs)
                    recyclerView.adapter = adapter
                } else {
                    // Handle the case where the API call was not successful
                    Log.e("API Error", response.message())
                }
            }
        }
    }
}
