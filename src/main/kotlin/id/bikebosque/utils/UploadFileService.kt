package id.bikebosque.utils

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import java.io.ByteArrayInputStream
import java.io.InputStream

object UploadFileService {

    private val serviceAccount : InputStream? =
        this::class.java.classLoader.getResourceAsStream("bars-account-service.json")

    fun uploadObject(fileByte: ByteArray, fileName :String){
        val storage = StorageOptions.newBuilder()
            .setProjectId(PROJECT_ID)
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build().service

        val blobId = BlobId.of(BUCKET_NAME, fileName)
        val blobInfo = BlobInfo.newBuilder(blobId).build()

        storage.createFrom(blobInfo, ByteArrayInputStream(fileByte))

        System.out.println("File $fileByte uploaded to bucket $BUCKET_NAME as $fileName")
    }

    fun getUrlImageGCS(objectName :String) =  "$BASE_URL_GCS$BUCKET_NAME/$objectName"

    const val BUCKET_NAME = "bars-avatar-image-bucket"
    const val PROJECT_ID = "bars-web-service"
    const val BASE_URL_GCS = "https://storage.googleapis.com/"

}