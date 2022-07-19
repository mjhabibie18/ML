package eu.tutorials.grafikkotlin.data

import com.google.gson.annotations.SerializedName

class getOrders{
    @SerializedName("odata.metadata")
    val message: String? = null
    @SerializedName("value")
    val data: ArrayList<Data> = arrayListOf()

class Data {
    @SerializedName("DocNum")
    var docNum: Int? = null

    @SerializedName("DocDate")
    var docDate: String? = null
    }
}


