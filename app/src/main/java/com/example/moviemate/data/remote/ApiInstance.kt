//package com.example.moviemate.data.remote
//
//import com.example.moviemate.util.AppConstants
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//
//class ApiInstance {
//    companion object {
//        private const val BASE_URL = AppConstants.BASE_URL
//
//        private val retrofitBuilder: Retrofit.Builder =
//            Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//    }
//
//    private val retrofit:Retrofit = retrofitBuilder.build()
//
//    private val httpClient: OkHttpClient.Builder
//            = OkHttpClient.Builder()
//
//    fun <T> createService(serviceClass: Class<T>): T {
//        return retrofit.create(serviceClass)
//    }
//}