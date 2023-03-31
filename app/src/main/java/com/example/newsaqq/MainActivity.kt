package com.example.newsaqq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsaqq.adapter.NewsAdapter
import com.example.newsaqq.api.NewsApi
import com.example.newsaqq.api.RetrofitClient
import com.example.newsaqq.databinding.ActivityMainBinding
import com.example.newsaqq.model.News
import com.example.newsaqq.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var newsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsList = binding.newsList
        getNews()
    }

    private fun getNews() {
        val country = "in" // replace with your desired country code
        val page = 2 // replace with your desired page number

        RetrofitClient.newsApi.getHeadlines(country, page).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                Log.d(TAG,response.body().toString())
                if (news!=null) {
                    Log.d(TAG,news.articles.toString())
                    adapter = NewsAdapter(this@MainActivity,news.articles)
                    newsList.adapter = adapter
                    newsList.layoutManager = LinearLayoutManager(this@MainActivity)
                } else {
                    Log.d(TAG,"Empty Check endpoints")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG,"Error in Fetching News",t)
            }
        })
    }
}
