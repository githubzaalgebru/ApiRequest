package com.algebra.apirequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity( ) {

    private lateinit var tvVicevi : TextView

    override fun onCreate( savedInstanceState : Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        tvVicevi = findViewById( R.id.tvVicevi )
/*
        val policy = ThreadPolicy.Builder( ).permitAll( ).build( )
        StrictMode.setThreadPolicy( policy );
*/
    }

    fun dohvati( b : View ) {
        BackgroundTask( tvVicevi ).execute( )
    }


}