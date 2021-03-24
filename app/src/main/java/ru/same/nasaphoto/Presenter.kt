package ru.same.nasaphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.same.nasaphoto.api.PostNasa
import java.io.IOException
import java.lang.Exception


class Presenter(var view: View) {
   private lateinit var postNasa:PostNasa
    private var date:String = ""
    private val key = "KLWjvXIygbbLfB6cYpqH3ji6Xlb45OvEckI7Mfu0"
    private var camera = "fhaz"

    fun setDate(string: String, cam:String){
        date = string
        camera = cam
    }
    fun getData(){
        view.showProgressBar()
        NasaApi.create().getData(date,camera, key)?.enqueue(object : Callback<PostNasa?>{

            override fun onResponse(call: Call<PostNasa?>, response: Response<PostNasa?>) {
                try {
                    postNasa = response.body()!!
                    view.setData("https://"+postNasa.photos[0].imgSrc.substringAfter("//"), date)
                    view.hideProgressBar()
                }catch (e: Exception){
                    view.hideProgressBar()
                    view.setData(null,"Rover isn't active")
                }
            }

            override fun onFailure(call: Call<PostNasa?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

//    fun getImage(){
//        val client: OkHttpClient = OkHttpClient()
//        val request = Request.Builder()
//            .url("https://"+postNasa.photos[0].imgSrc.substringAfter("//")).build()
//        client.newCall(request).enqueue(object : okhttp3.Callback{
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
//              val img = response.body()?.bytes()
//                val bitmap = img?.size?.let { BitmapFactory.decodeByteArray(img,0, it) }
//                bitmap?.let { view.setData(it, date) }
//                view.hideProgressBar()
//            }
//
//        })
//    }

    interface View{
        fun setData(image: String?, date: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun showCalendar()
    }
}

