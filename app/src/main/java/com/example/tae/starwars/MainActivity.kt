package com.example.tae.starwars

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.tae.starwars.R.id.btnSubmit
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://swapi.co/api/people/")
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = retrofitBuilder
            .client(okHttpClient)
            .build()

        val starwarsClient = retrofit.create(StarwarsServices::class.java)

        btnSubmit.setOnClickListener{
            starwarsClient.getUserDetails(etInput.text.toString()).enqueue(object : Callback<StarwarsUser> {
                override fun onFailure(call: Call<StarwarsUser>, throwable: Throwable) {
                    throwable.printStackTrace()

                }

                override fun onResponse(call: Call<StarwarsUser>, response: Response<StarwarsUser>) {
                    if (response.isSuccessful) {
                        val githubUser = response.body()
                        tvName.text = githubUser?.name
                        tvHeight.text = githubUser?.height
                        tvGenger.text = githubUser?.gender
                    }

                }
            })

        }
    }
}



