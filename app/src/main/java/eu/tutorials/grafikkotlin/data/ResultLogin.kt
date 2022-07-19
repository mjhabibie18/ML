package eu.tutorials.grafikkotlin.data

import com.google.gson.annotations.SerializedName

class ResultLogin {
    @SerializedName("odata.metadata")
    val odata: String? = null
    @SerializedName("SessionId")
    val sessionId: String? = null
    @SerializedName("Version")
    val version: String? = null
    @SerializedName("SessionTimeout")
    val sessionTimeOut: Integer? = null


}