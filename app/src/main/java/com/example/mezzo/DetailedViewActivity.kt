package com.example.mezzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat

class DetailedViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        // Initialize UI elements from the layout
        val btnDisplayPlaylist = findViewById<Button>(R.id.btnDisplayPlaylist)
        val btnCalcAverage = findViewById<Button>(R.id.btnCalcAverage)
        val btnReturn = findViewById<Button>(R.id.btnReturn)
        val tvPlaylistDetails = findViewById<TextView>(R.id.tvPlaylistDetails)

        Log.i("DetailedViewActivity", "DetailedViewActivity created successfully.")

        // Set click listener to display the full playlist
        btnDisplayPlaylist.setOnClickListener {
            Log.d("DetailedViewActivity", "Display Playlist button clicked.")
            if (MainActivity.songs.isEmpty()) {
                tvPlaylistDetails.text = "Your playlist is empty."
                Toast.makeText(this, "Nothing to show.", Toast.LENGTH_SHORT).show()
            } else {
                val listBuilder = StringBuilder()
                for (i in MainActivity.songs.indices) {
                    listBuilder.append("Song: ${MainActivity.songs[i]}\n")
                    listBuilder.append("Artist: ${MainActivity.artists[i]}\n")
                    listBuilder.append("Rating: ${MainActivity.ratings[i]}/5\n")
                    listBuilder.append("Comments: ${MainActivity.comments[i]}\n\n")
                }
                tvPlaylistDetails.text = listBuilder.toString()
            }
        }

        // Set click listener to calculate and display the average rating
        btnCalcAverage.setOnClickListener {
            Log.d("DetailedViewActivity", "Calculate Average Rating button clicked.")
            if (MainActivity.ratings.isEmpty()) {
                tvPlaylistDetails.text = "No songs to calculate rating."
                Toast.makeText(this, "Playlist is empty.", Toast.LENGTH_SHORT).show()
            } else {
                var totalRating = 0
                for (rating in MainActivity.ratings) {
                    totalRating += rating
                }
                val averageRating = totalRating.toDouble() / MainActivity.ratings.size
                val df = DecimalFormat("#.##") // Format to two decimal places
                val averageText = "Average Playlist Rating:\n${df.format(averageRating)} / 5.00"
                tvPlaylistDetails.text = averageText
                Log.i("DetailedViewActivity", "Average rating calculated: $averageRating")
            }
        }

        // Set click listener to return to the main screen
        btnReturn.setOnClickListener {
            Log.d("DetailedViewActivity", "Return to Main Screen button clicked.")
            finish() // Closes the current activity
        }
    }
}