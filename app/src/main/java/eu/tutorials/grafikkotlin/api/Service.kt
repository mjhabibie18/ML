package eu.tutorials.grafikkotlin.api

import eu.tutorials.grafikkotlin.data.Login
import eu.tutorials.grafikkotlin.data.ResultLogin
import eu.tutorials.grafikkotlin.data.getOrders
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @POST("Login")
    fun login(@Body body: Login) : Call<ResponseBody>

    @GET("Orders")
    fun getOrders(@Header("SessionId") token : String) : Call<getOrders>
}
