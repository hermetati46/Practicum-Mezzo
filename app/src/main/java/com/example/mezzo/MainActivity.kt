package com.example.mezzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    // Declare parallel arrays to store playlist data
    companion object {
        val songs = ArrayList<String>()
        val artists = ArrayList<String>()
        val ratings = ArrayList<Int>()
        val comments = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons from the layout
        val btnAddToPlaylist = findViewById<Button>(R.id.btnAddToPlaylist)
        val btnGoToDetailedView = findViewById<Button>(R.id.btnGoToDetailedView)
        val btnExit = findViewById<Button>(R.id.btnExit)

        // Log to show the main activity has been created
        Log.i("MainActivity", "MainActivity created successfully for Music App.")

        // Pre-populate with example data if the list is empty
        if (songs.isEmpty()) {
            addInitialSongs()
            Log.i("MainActivity", "Initial song data loaded.")
        }

        // Set click listener for the "Add to Playlist" button
        btnAddToPlaylist.setOnClickListener {
            Log.d("MainActivity", "Add to Playlist button clicked.")
            showAddSongDialog()
        }

        // Set click listener to navigate to the detailed view screen
        btnGoToDetailedView.setOnClickListener {
            Log.d("MainActivity", "Navigate to DetailedViewActivity button clicked.")
            val intent = Intent(this, DetailedViewActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the "Exit" button
        btnExit.setOnClickListener {
            Log.d("MainActivity", "Exit button clicked.")
            finishAffinity() // Closes the app
        }
    }

    // Function to show a dialog for adding a new song
    private fun showAddSongDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Song")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_song, null)
        val etSongTitle = dialogLayout.findViewById<EditText>(R.id.etSongTitle)
        val etArtistName = dialogLayout.findViewById<EditText>(R.id.etArtistName)
        val etRating = dialogLayout.findViewById<EditText>(R.id.etRating)
        val etComments = dialogLayout.findViewById<EditText>(R.id.etComments)

        builder.setView(dialogLayout)

        // Set the "Add" button and its click listener
        builder.setPositiveButton("Add") { dialog, _ ->
            val songTitle = etSongTitle.text.toString()
            val artistName = etArtistName.text.toString()
            val ratingStr = etRating.text.toString()
            val commentsText = etComments.text.toString()

            // Error handling for empty fields and invalid rating
            if (songTitle.isNotBlank() && artistName.isNotBlank() && ratingStr.isNotBlank()) {
                try {
                    val rating = ratingStr.toInt()
                    if (rating in 1..5) {
                        songs.add(songTitle)
                        artists.add(artistName)
                        ratings.add(rating)
                        comments.add(commentsText)
                        Toast.makeText(this, "Song Added!", Toast.LENGTH_SHORT).show()
                        Log.i("MainActivity", "New song '$songTitle' added to the playlist.")
                    } else {
                        Toast.makeText(this, "Rating must be between 1 and 5.", Toast.LENGTH_LONG).show()
                        Log.e("MainActivity", "Invalid rating entered: $rating")
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter a valid number for rating.", Toast.LENGTH_LONG).show()
                    Log.e("MainActivity", "NumberFormatException for rating input: $ratingStr")
                }
            } else {
                Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_LONG).show()
                Log.w("MainActivity", "Attempted to add a song with empty fields.")
            }
        }

        // Set the "Cancel" button
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // Function to add initial data
    private fun addInitialSongs() {
        songs.addAll(listOf("Bohemian Rhapsody", "Hotel California", "Stairway to Heaven", "Like a Rolling Stone"))
        artists.addAll(listOf("Queen", "Eagles", "Led Zeppelin", "Bob Dylan"))
        ratings.addAll(listOf(5, 5, 4, 4))
        comments.addAll(listOf("Iconic classic", "Timeless rock anthem", "Epic guitar solo", "Folk-rock masterpiece"))
    }
}