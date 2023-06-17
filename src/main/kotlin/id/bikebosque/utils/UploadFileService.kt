package id.bikebosque.utils

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.google.gson.Gson
import java.io.File

class UploadFileService {

    private var cloudinary = Cloudinary(
        ObjectUtils.asMap(
        "cloud_name", "dkhmfndoq",
        "api_key", "344641951313548",
        "api_secret","lBrevQLxPV8nl0Tzzp_lzsv68ec"
        )
    )

    fun uploadFile(file :File) = cloudinary.uploader().upload(file, ObjectUtils.emptyMap())


}