package id.bikebosque.models.response

import com.google.gson.Gson
import io.ktor.http.*

data class BaseResponse(
    var statusCode :Int = 200,
    var message :String = ""
){
    companion object{
        fun toJsonString(httpStatusCode: HttpStatusCode?) :String{
            return Gson().toJson(
                BaseResponse(httpStatusCode?.value ?: 500, httpStatusCode?.description ?: "N/A")
            )
        }
    }
}