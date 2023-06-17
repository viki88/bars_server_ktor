package id.bikebosque.models.response

data class UploadImageResponse(
    var url :String = "",
    var statusCode :Int = 200,
    var message :String
)
