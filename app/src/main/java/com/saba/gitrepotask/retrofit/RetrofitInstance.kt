package com.saba.gitrepotask.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    /**companion object initializes when the class is loaded for the first time.
     * so anything inside a companion object can be accessed through the class name.
     */
    companion object{

        val BASE_URL = "https://api.github.com/"
        /*val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }*/
        /*val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor).build()
        }*/
        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                //default time out is 10 seconds
                .connectTimeout(30, TimeUnit.SECONDS)//max time to try establishing connection
                .readTimeout(20, TimeUnit.SECONDS) //max time btwn 2 data packets from server
                .writeTimeout(25, TimeUnit.SECONDS) //max time btwm 2 data packets when sending to server

        }.build()
        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}