package id.bikebosque.utils

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.util.*

class UploadFileService {

    val prop = Properties().apply {
        load(FileInputStream(File("local.properties")))
    }

    private var cloudinary = Cloudinary(
        ObjectUtils.asMap(
        "cloud_name", prop.getProperty("cloudinary_cloud_name"),
        "api_key", prop.getProperty("cloudinary_api_key"),
        "api_secret", prop.getProperty("cloudinary_api_secret")
        )
    )

    fun uploadFile(file :File) = cloudinary.uploader().upload(file, ObjectUtils.emptyMap())


}