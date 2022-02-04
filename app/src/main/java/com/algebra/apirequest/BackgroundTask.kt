package com.algebra.apirequest

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

const val URL_API = "https://api.icndb.com/jokes/random/3"

class BackgroundTask( val tvVicevi : TextView ) : AsyncTask< Unit, Unit, String >( ) {

    val TAG = "BackgroundTask"

    override fun doInBackground( vararg p0: Unit? ) : String {
        val sb = StringBuilder( "" )
        val url = URL( URL_API )
        val connection = url.openConnection( )
        BufferedReader( InputStreamReader( connection.getInputStream( ) ) ).use { inp ->
            var line: String?
            while ( inp.readLine( ).also { line = it } != null) {
                sb.append( "\n$line" )
            }
        }
        val data = sb.toString( ).substring( 1 )
        Log.i( TAG, "Data from remote server (background thread): $data" )
        return data
    }

    override fun onPostExecute( result : String? ) {
        Log.i( TAG, "Data from background thread (UI thread): $result" )
        val response = getResponse( result!! )

        var ispis = ""
        response.value.forEach {
            ispis += "\n\n\n${ it.joke }"
        }
        tvVicevi.text = ispis.substring( 3 )
    }

    fun getResponse( response : String ) : Response {
        val jsonResponse = JSONObject( response )
        val type = jsonResponse.getString( "type" )
        val value = getJokes( jsonResponse.getJSONArray( "value" ) )
        return Response( type, value )
    }

    fun getJokes( jsonJokes : JSONArray) : List< Joke > {
        val jokes = mutableListOf< Joke >( )
        for( i in 0 until jsonJokes.length( ) )
            jokes.add( getJoke( jsonJokes.getJSONObject( i ) ) )
        return jokes.toList( )
    }

    fun getJoke( jsonJoke : JSONObject) : Joke {
        val id = jsonJoke.getInt( "id" )
        val joke = jsonJoke.getString( "joke" ).replace( "&quot;", "\"")
        val categories = getCategories( jsonJoke.getJSONArray( "categories" ) )
        return Joke( id, joke, categories )
    }

    fun getCategories( jsonCategories : JSONArray) : List< String > {
        val categories = mutableListOf< String >( )
        for( i in 0 until  jsonCategories.length( ) )
            categories.add( jsonCategories.getString( i ) )
        return categories.toList( )
    }
}

class Joke( val id : Int, val joke : String, val categories : List< String > )
class Response( val type : String, val value : List< Joke > )