package com.example.myfirstapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfirstapplication.model.Song

// Define the SongAdapter class which extends RecyclerView.Adapter
class SongAdapter(private val songs: List<Song>) :
    RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    // Define a listener for item click events
    private var onItemClickListener: ((Song) -> Unit)? = null

    // Define a ViewHolder class to hold references to UI elements
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val artistTextView: TextView = view.findViewById(R.id.artistTextView)
        val coverImageView: ImageView = view.findViewById(R.id.coverImageView)
    }

    // Override the onCreateViewHolder method to create ViewHolders for the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item_song layout and create a new ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    // Override the onBindViewHolder method to bind data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the current song from the list
        val song = songs[position]

        // Set the title and artist text views with data from the current song
        holder.titleTextView.text = song.title
        holder.artistTextView.text = song.artist.name

        // Load and display the image using the Glide library
        Glide.with(holder.itemView)
            .load(song.album.cover_medium)
            .into(holder.coverImageView)

        // Set click listener for the item view to handle item click events
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(song)
        }
    }

    // Override the getItemCount method to return the number of items in the list
    override fun getItemCount(): Int {
        return songs.size
    }

    // Define a method to set the item click listener externally
    fun setOnItemClickListener(listener: (Song) -> Unit) {
        onItemClickListener = listener
    }
}
