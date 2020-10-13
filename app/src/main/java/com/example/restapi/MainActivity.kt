package com.example.restapi

import android.os.AsyncTask
import android.os.Bundle
import android.util.JsonReader
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AsyncTask.execute {
            // Create URL
            val githubEndpoint = URL("https://api.github.com/")
            // Create connection
            val myConnection: HttpsURLConnection = githubEndpoint.openConnection() as HttpsURLConnection

            // Add request header
//            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1")
//            myConnection.setRequestProperty("Accept", "application/vnd.github.v3+json")
//            myConnection.setRequestProperty("Contact-Me", "hathibelagal@example.com")

            // Read response
            if (myConnection.responseCode == 200) {
                // Success
                val responseBody: InputStream = myConnection.inputStream
                val responseBodyReader = InputStreamReader(responseBody, "UTF-8")
                val jsonReader = JsonReader(responseBodyReader)
                jsonReader.beginObject() // Start processing the JSON object
                while (jsonReader.hasNext()) { // Loop through all keys
                    val key = jsonReader.nextName() // Fetch the next key
                    if (key == "current_user_url") { // Check if desired key
                        // Fetch the value as a String
                        val value = jsonReader.nextString()

                        // Do something with the value
                        println("value: $value")
                        break // Break out of the loop
                    } else {
                        jsonReader.skipValue() // Skip values of other keys
                    }
                }

                jsonReader.close()
                myConnection.disconnect()
            } else {
                // Error handling code goes here
                println("ERROR")
            }
        }
    }
}